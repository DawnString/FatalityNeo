package cn.dawnstring.fatality.item.weapon.projectile.behavior;

import cn.dawnstring.fatality.item.weapon.projectile.ProjectileBehavior;
import cn.dawnstring.fatality.item.weapon.projectile.WeaponProjectile;

/**
 * 直线飞行。每 tick 按 stats.speed 移动。
 * 如果 stats.gravity 为 true，重力已在 WeaponProjectile.tick() 中应用。
 */
public class StraightFlight implements ProjectileBehavior
{
    @Override
    public void onTick(WeaponProjectile projectile)
    {
        // 移动已在 WeaponProjectile.tickServer() 中通过 setDeltaMovement 处理
        // 此行为仅作为标识，确保弹幕沿直线飞行
    }
}
