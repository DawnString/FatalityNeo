package cn.dawnstring.fatality.item.weapon.projectile.behavior;

import cn.dawnstring.fatality.item.weapon.projectile.ProjectileBehavior;
import cn.dawnstring.fatality.item.weapon.projectile.WeaponProjectile;

/**
 * 穿透实体。命中时不销毁，计数交给 WeaponProjectile 内部的 pierceCount 管理。
 */
public class PierceEntities implements ProjectileBehavior
{
    @Override
    public void onHitEntity(ProjectileHitContext ctx)
    {
        // 穿透逻辑已在 WeaponProjectile.onHitEntity() 中处理
        // 此行为标记弹幕可穿透，并触发弹幕继续飞行
    }
}
