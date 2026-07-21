package cn.dawnstring.fatality.item.weapon.projectile;

/**
 *
 * @param speed 速度 block/tick
 * @param damage 伤害，由WeaponHandler传入
 * @param maxPierce 最大穿透数
 * @param maxBounce 最大 弹射 数
 * @param lifetime 存活时间
 * @param size 碰撞箱大小
 * @param gravity 是否受重力
 * @param textureId 纹理
 */
public record ProjectileStats(
        float speed,
        float damage,
        int maxPierce,
        int maxBounce,
        int lifetime,
        float size,
        boolean gravity,
        String textureId
) { }
