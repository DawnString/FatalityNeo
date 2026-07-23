package cn.dawnstring.fatality.item.weapon.projectile.behavior;

import cn.dawnstring.fatality.core.combat.DamageHandler;
import cn.dawnstring.fatality.item.weapon.projectile.ProjectileBehavior;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class AoeOnHit implements ProjectileBehavior
{
    private final float radius;
    private final float damageMultiplier;
    private final boolean onEntityHit;

    /** 仅在命中方块时触发 AoE */
    public AoeOnHit(float radius, float damageMultiplier)
    {
        this(radius, damageMultiplier, false);
    }

    /**
     * @param radius        范围半径
     * @param damageMultiplier 伤害倍率 (相对于弹幕基础伤害)
     * @param onEntityHit   命中实体时是否也触发 (默认false，贯穿类弹幕建议false)
     */
    public AoeOnHit(float radius, float damageMultiplier, boolean onEntityHit)
    {
        this.radius = radius;
        this.damageMultiplier = damageMultiplier;
        this.onEntityHit = onEntityHit;
    }

    @Override
    public void onHitEntity(ProjectileHitContext ctx)
    {
        if (onEntityHit) doAoe(ctx);
    }

    @Override
    public void onHitBlock(ProjectileHitContext ctx)
    {
        doAoe(ctx);
    }

    private void doAoe(ProjectileHitContext ctx)
    {
        var projectile = ctx.projectile();
        var level = projectile.level();
        if (level.isClientSide()) return;

        var stats = projectile.getStats();
        if (stats == null) return;

        Vec3 center = projectile.position();
        float totalDamage = stats.damage() * damageMultiplier;

        Entity owner = projectile.getOwner();
        DamageSource source = owner instanceof Player p
                ? p.damageSources().playerAttack(p)
                : level.damageSources().generic();

        AABB area = AABB.ofSize(center, radius * 2, radius * 2, radius * 2);
        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class, area,
                e -> e != owner && e.isAlive() && e.distanceToSqr(center) <= radius * radius
        );

        DamageHandler.WEAPON_HANDLER_ACTIVE.set(true);
        try
        {
            for (LivingEntity target : targets)
            {
                target.hurt(source, totalDamage);
            }
        }
        finally
        {
            DamageHandler.WEAPON_HANDLER_ACTIVE.set(false);
        }
    }
}
