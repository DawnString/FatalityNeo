package cn.dawnstring.fatality.register;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.item.weapon.projectile.WeaponProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntityTypes
{
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(Registries.ENTITY_TYPE, Fatality.MODID);

    public static final Supplier<EntityType<WeaponProjectile>> WEAPON_PROJECTILE =
            ENTITY_TYPES.register("weapon_projectile",
                    () -> EntityType.Builder.<WeaponProjectile>of(
                            WeaponProjectile::new, MobCategory.MISC)
                            .sized(0.25f, 0.25f)
                            .clientTrackingRange(8)
                            .updateInterval(2)
                            .build("weapon_projectile")
            );

    public static void register(IEventBus modEventBus)
    {
        ENTITY_TYPES.register(modEventBus);
    }
}
