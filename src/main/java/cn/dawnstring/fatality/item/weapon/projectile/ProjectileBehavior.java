package cn.dawnstring.fatality.item.weapon.projectile;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

public interface ProjectileBehavior
{
    default void onTick(WeaponProjectile projectile) {}

    default void onHitEntity(ProjectileHitContext ctx) {}

    default void onHitBlock(ProjectileHitContext ctx) {}

    record ProjectileHitContext(
            WeaponProjectile projectile,
            @Nullable Entity hitEntity,
            HitResult hitResult
    )
    {
        public boolean hasHitEntity() { return hitEntity != null; }
    }
}
