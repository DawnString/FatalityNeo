package cn.dawnstring.fatality.item.weapon.melee;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.UniqueItemType;
import cn.dawnstring.fatality.item.weapon.*;
import cn.dawnstring.fatality.item.weapon.projectile.ProjectileBehavior;
import cn.dawnstring.fatality.item.weapon.projectile.ProjectileStats;
import cn.dawnstring.fatality.item.weapon.projectile.WeaponProjectile;
import cn.dawnstring.fatality.item.weapon.projectile.behavior.AoeOnHit;
import cn.dawnstring.fatality.item.weapon.projectile.behavior.StraightFlight;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.register.ModEntityTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@AutoItem(itemId = "wave_breaker", category = ItemCategory.WEAPON)
public class WaveBreaker extends WeaponItem
{
    public WaveBreaker()
    {
        super(
                new Item.Properties().stacksTo(1),
                STATS,
                List.of(),
                THROW_ACTIVE
        );
        setUniqueItemTypeDes(UniqueItemType.SUPPORTER_ITEM);
        setUniqueDes(Component.translatable("item.fatality.wave_breaker.unique"));
    }

    private static final WeaponStats STATS = new WeaponStats(
            420.0f,
            0.14f,
            0.16f,
            0.05f,
            0.50f,
            WeaponType.MELEE,
            AttackMode.SWING,
            0,
            10
    );

    private static final ProjectileStats THROW_STATS = new ProjectileStats(
            2.5f,
            504.0f,
            10,
            0,
            60,
            1.0f,
            false,
            "fatality:textures/projectile/wave_breaker.png"
    );

    private static final List<ProjectileBehavior> BEHAVIORS = List.of(
            new StraightFlight(),
            new AoeOnHit(3.0f, 0.8f)
    );

    private static final WeaponActive THROW_ACTIVE = new WeaponActive(40, 0)
    {
        @Override
        public String getNameKey()
        {
            return "active.fatality.wave_breaker.throw";
        }

        @Override
        public void execute(Player player, Level level)
        {
            if (level.isClientSide()) return;

            Vec3 lookDir = player.getLookAngle();

            var projectile = new WeaponProjectile(
                    ModEntityTypes.WEAPON_PROJECTILE.get(),
                    level,
                    player,
                    lookDir,
                    THROW_STATS,
                    BEHAVIORS)
                    ;
            level.addFreshEntity(projectile);
        }
    };
}
