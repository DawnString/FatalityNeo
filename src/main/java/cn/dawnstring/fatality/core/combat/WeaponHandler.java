package cn.dawnstring.fatality.core.combat;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.item.weapon.WeaponActive;
import cn.dawnstring.fatality.item.weapon.WeaponItem;
import cn.dawnstring.fatality.item.weapon.WeaponPassive;
import cn.dawnstring.fatality.item.weapon.projectile.WeaponProjectile;
import cn.dawnstring.fatality.network.C2SAttackIntent;
import cn.dawnstring.fatality.register.ModEntityTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端武器路由调度器。
 * 接收 C2SAttackIntent，校验后按 AttackMode 分发攻击逻辑。
 */
public class WeaponHandler
{
    /** BEAM 模式：正在持续施法的玩家集合 */
    private static final Set<UUID> BEAM_ACTIVE = ConcurrentHashMap.newKeySet();

    public static void dispatch(Player player, C2SAttackIntent packet)
    {
        ItemStack held = player.getMainHandItem();
        if (!(held.getItem() instanceof WeaponItem weapon)) return;

        // 右键技能
        if (packet.actionType() == 1)
        {
            handleActive(player, weapon);
            return;
        }

        // BEAM 模式松开
        if (packet.actionType() == 2)
        {
            BEAM_ACTIVE.remove(player.getUUID());
            return;
        }

        // 冷却检查
        if (player.getCooldowns().isOnCooldown(weapon)) return;

        // 攻击模式校验
        if (packet.attackMode() != weapon.getWeaponStats().attackMode().ordinal())
        {
            Fatality.LOGGER.warn("AttackMode mismatch: packet={}, weapon={}",
                    packet.attackMode(), weapon.getWeaponStats().attackMode());
            return;
        }

        switch (weapon.getWeaponStats().attackMode())
        {
            case SWING -> handleSwing(player, weapon);
            case PROJECTILE -> handleProjectile(player, weapon, packet);
            case BEAM -> handleBeam(player);
            case SPECIAL -> handleSpecial(player, weapon, packet);
        }
    }

    // ========== SWING：近战挥砍 ==========

    private static void handleSwing(Player player, WeaponItem weapon)
    {
        LivingEntity target = findTarget(player, player.isCreative() ? 5.0 : 3.0);
        if (target != null)
        {
            DamageSource source = player.damageSources().playerAttack(player);

            // 计算伤害
            float damage = DamageHandler.compute(player, target, weapon, source);
            if (damage <= 0) return;

            // 执行伤害（标记使 MixinLivingEntityHurt 跳过重复计算）
            DamageHandler.WEAPON_HANDLER_ACTIVE.set(true);
            try
            {
                target.hurt(source, damage);
            }
            finally
            {
                DamageHandler.WEAPON_HANDLER_ACTIVE.set(false);
            }

            // 触发被动效果
            boolean isCrit = Boolean.TRUE.equals(DamageHandler.LAST_HIT_CRIT.get());
            var ctx = new WeaponPassive.WeaponHitContext(
                    player, target, player.getMainHandItem(), damage, isCrit);
            for (WeaponPassive passive : weapon.getPassives())
                passive.onHit(ctx);
        }

        // 无论是否命中都触发冷却
        player.getCooldowns().addCooldown(weapon, weapon.getWeaponStats().cooldownTicks());
    }

    // ========== PROJECTILE：发射弹幕 ==========

    private static void handleProjectile(Player player, WeaponItem weapon, C2SAttackIntent packet)
    {
        // 魔力检查
        int manaCost = weapon.getWeaponStats().manaCost();
        if (manaCost > 0 && !RegenSystem.consumeMana(player, manaCost))
            return; // 魔力不足

        // 方向校验（客户端发包方向与玩家视线偏差不超过 15°）
        Vec3 packetDir = new Vec3(packet.dirX(), packet.dirY(), packet.dirZ()).normalize();
        Vec3 lookDir = player.getLookAngle();
        if (packetDir.dot(lookDir) < Math.cos(Math.toRadians(15)))
        {
            Fatality.LOGGER.warn("Projectile direction rejected for {}: deviation too large",
                    player.getName().getString());
            return;
        }

        // 创建弹幕实体
        var pStats = weapon.getProjectileStats();
        if (pStats == null)
        {
            Fatality.LOGGER.warn("{} has no ProjectileStats configured", weapon);
            return;
        }

        var projectile = new WeaponProjectile(
                ModEntityTypes.WEAPON_PROJECTILE.get(),
                player.level(), player, packetDir,
                pStats, weapon.getProjectileBehaviors());
        player.level().addFreshEntity(projectile);

        player.getCooldowns().addCooldown(weapon, weapon.getWeaponStats().cooldownTicks());
    }

    // ========== BEAM：持续光束/喷火器 ==========

    private static void handleBeam(Player player)
    {
        BEAM_ACTIVE.add(player.getUUID());
        // TODO: 持续伤害逻辑在 ServerTickEvent 中遍历 BEAM_ACTIVE
    }

    // ========== SPECIAL：特殊剑 ==========

    private static void handleSpecial(Player player, WeaponItem weapon, C2SAttackIntent packet)
    {
        // TODO: 特殊剑自定义行为，由各武器子类通过 passives 或 activeAbility 实现
        handleSwing(player, weapon);
    }

    // ========== 右键主动技能 ==========

    private static void handleActive(Player player, WeaponItem weapon)
    {
        WeaponActive active = weapon.getActiveAbility();
        if (active == null) return;

        if (player.getCooldowns().isOnCooldown(weapon)) return;

        int manaCost = active.getManaCost();
        if (manaCost > 0 && !RegenSystem.consumeMana(player, manaCost))
            return;

        active.execute(player, player.level());

        if (active.getCooldownTicks() > 0)
            player.getCooldowns().addCooldown(weapon, active.getCooldownTicks());
    }

    // ========== BEAM 持续施法 tick（由 ServerTickEvent 调用） ==========

    public static void tickBeams()
    {
        // TODO: 遍历 BEAM_ACTIVE，对每个玩家持光束武器持续造成伤害
    }

    // ========== 工具：近战目标检测 ==========

    @Nullable
    private static LivingEntity findTarget(Player player, double reach)
    {
        Vec3 eyePos = player.getEyePosition();
        Vec3 look = player.getLookAngle();
        Vec3 end = eyePos.add(look.x * reach, look.y * reach, look.z * reach);

        AABB searchBox = player.getBoundingBox()
                .expandTowards(look.scale(reach))
                .inflate(1.0);

        LivingEntity closest = null;
        double closestDist = Double.MAX_VALUE;

        for (LivingEntity entity : player.level().getEntitiesOfClass(
                LivingEntity.class, searchBox, e -> e != player && e.isAlive()))
        {
            AABB bb = entity.getBoundingBox().inflate(0.3);
            var hit = bb.clip(eyePos, end);
            if (hit.isPresent())
            {
                double dist = hit.get().distanceToSqr(eyePos);
                if (dist < closestDist)
                {
                    closestDist = dist;
                    closest = entity;
                }
            }
        }
        return closest;
    }
}
