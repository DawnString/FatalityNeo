package cn.dawnstring.fatality.item.weapon.melee;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.UniqueItemType;
import cn.dawnstring.fatality.item.weapon.*;
import cn.dawnstring.fatality.item.weapon.projectile.ProjectileBehavior;
import cn.dawnstring.fatality.item.weapon.projectile.ProjectileStats;
import cn.dawnstring.fatality.item.weapon.projectile.WeaponProjectile;
import cn.dawnstring.fatality.item.weapon.projectile.behavior.TrackingFlight;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.register.ModEntityTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@AutoItem(itemId = "dragon_fang_lance", category = ItemCategory.WEAPON)
public class DragonFangLance extends WeaponItem
{
    public DragonFangLance()
    {
        super(
                new Item.Properties().stacksTo(1),
                STATS,
                List.of(),
                THROW_ACTIVE
        );

        setUniqueItemTypeDes(UniqueItemType.SUPPORTER_ITEM);
        setUniqueDes(Component.translatable("item.fatality.dragon_fang_lance.unique"));
    }

    private static final WeaponStats STATS = new WeaponStats(
            850.0f,
            0.10f,
            0.18f,
            0.06f,
            0.50f,
            WeaponType.MELEE,
            AttackMode.SWING,
            0,
            10
    );

    private static final ProjectileStats FANG_STATS = new ProjectileStats(
            2.5f,
            340.0f,
            0,
            0,
            40,
            0.8f,
            false,
            "fatality:textures/projectile/dragon_fang.png"
    );

    private static final List<ProjectileBehavior> FANG_BEHAVIORS = List.of(
            new TrackingFlight(15.0f, 0.15f, 20)
    );

    private static final WeaponActive THROW_ACTIVE = new WeaponActive(40, 0)
    {
        @Override
        public String getNameKey()
        {
            return "active.fatality.dragon_fang_lance.throw";
        }

        @Override
        public void execute(Player player, Level level)
        {
            if (level.isClientSide()) return;

            Vec3 lookDir = player.getLookAngle();
            Vec3 up = player.getUpVector(1.0f);
            Vec3 right = lookDir.cross(up).normalize();

            // 扇形散布：-15°, 0°, 15°
            float spread = 0.26f; // ~15°弧度
            float offset = 0.3f;  // 横向偏移避免重叠

            for (int i = -1; i <= 1; i++)
            {
                Vec3 spreadDir = lookDir.add(right.scale(spread * i)).normalize();

                var projectile = new WeaponProjectile(
                        ModEntityTypes.WEAPON_PROJECTILE.get(),
                        level, player, spreadDir,
                        FANG_STATS, FANG_BEHAVIORS);
                projectile.setPos(projectile.position().add(right.scale(offset * i)));
                level.addFreshEntity(projectile);
            }
        }
    };
}
