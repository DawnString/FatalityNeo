package cn.dawnstring.fatality.item.weapon.melee;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.UniqueItemType;
import cn.dawnstring.fatality.item.weapon.*;
import cn.dawnstring.fatality.item.weapon.projectile.ProjectileStats;
import cn.dawnstring.fatality.item.weapon.projectile.WeaponProjectile;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.register.ModEntityTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@AutoItem(itemId = "blade_of_masters_fall", category = ItemCategory.WEAPON)
public class BladeOfMastersFall extends WeaponItem
{
    public BladeOfMastersFall()
    {
        super(
                new Item.Properties().stacksTo(1),
                STATS,
                List.of(),
                BEAM_ACTIVE
        );

        setUniqueItemTypeDes(UniqueItemType.SUPPORTER_ITEM);
        setUniqueDes(Component.translatable("item.fatality.blade_of_masters_fall.unique"));
    }

    private static final WeaponStats STATS = new WeaponStats(
            500.0f,
            0.12f,
            0.15f,
            0.04f,
            0.50f,
            WeaponType.MELEE,
            AttackMode.SWING,
            0,
            8
    );

    private static final ProjectileStats BEAM_STATS = new ProjectileStats(
            3.0f,
            300.0f,
            3,
            0,
            30,
            0.4f,
            false,
            "fatality:textures/projectile/blade_of_masters_fall.png"
    );

    private static final WeaponActive BEAM_ACTIVE = new WeaponActive(30, 0)
    {
        @Override
        public String getNameKey()
        {
            return "active.fatality.blade_of_masters_fall.beam";
        }

        @Override
        public void execute(Player player, Level level)
        {
            if (level.isClientSide()) return;

            Vec3 lookDir = player.getLookAngle();

            var projectile = new WeaponProjectile(
                    ModEntityTypes.WEAPON_PROJECTILE.get(),
                    level, player, lookDir,
                    BEAM_STATS, List.of());
            level.addFreshEntity(projectile);
        }
    };

    /**
     * 每损失 1% 最大生命值，提升 1.5% 伤害。
     * 例：剩余 50% HP → 损失 50% → 倍率 = 1 + 50×0.015 = 1.75
     */
    @Override
    public float getDamageMultiplier(Player player, LivingEntity target)
    {
        float maxHp = player.getMaxHealth();
        float currentHp = player.getHealth();
        float percentLost = (maxHp - currentHp) / maxHp * 100f;
        return 1.0f + percentLost * 0.015f;
    }

    /**
     * 持有时每 0.5 秒（10 tick）消耗 1% 最大生命值，不低于 1 点。
     * 玩家剩余 5% 最大血量时停止。
     */
    @Override
    public void onServerTick(Player player)
    {
        if (player.isCreative()) return;
        if (player.level().getGameTime() % 10 != 0) return;

        float maxHp = player.getMaxHealth();
        float currentHp = player.getHealth();

        // 剩余 5% 以下停止
        float minHp = maxHp * 0.05f;
        if (currentHp <= minHp) return;

        // 消耗 1% 最大生命值，不低于 1 点
        float cost = Math.max(1.0f, maxHp * 0.01f);
        // 避免扣到 5% 以下
        cost = Math.min(cost, currentHp - minHp);

        player.hurt(player.damageSources().magic(), cost);
    }
}
