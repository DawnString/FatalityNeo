package cn.dawnstring.fatality.item.weapon.projectile.behavior;

import cn.dawnstring.fatality.item.weapon.projectile.ProjectileBehavior;
import net.minecraft.world.level.Level;

public class ExplodeOnHit implements ProjectileBehavior
{
    private final float radius;
    private final float damagePercent;

    /**
     * 命中时产生爆炸。
     * @param radius        爆炸半径
     * @param damagePercent 爆炸伤害倍率 (相对于弹幕基础伤害)
     */
    public ExplodeOnHit(float radius, float damagePercent)
    {
        this.radius = radius;
        this.damagePercent = damagePercent;
    }

    @Override
    public void onHitEntity(ProjectileHitContext ctx)
    {
        explode(ctx);
    }

    @Override
    public void onHitBlock(ProjectileHitContext ctx)
    {
        explode(ctx);
    }

    private void explode(ProjectileHitContext ctx)
    {
        Level level = ctx.projectile().level();
        if (level.isClientSide()) return;

        var pos = ctx.projectile().position();
        float baseDamage = ctx.projectile().getStats() != null
                ? ctx.projectile().getStats().damage() * damagePercent
                : 0;

        level.explode(
                ctx.projectile(),
                pos.x, pos.y, pos.z,
                radius,
                Level.ExplosionInteraction.NONE
        );
        // 注：level.explode 已包含伤害和击退
        // 如果需要自定义伤害倍率而非标准爆炸公式，需用 getEntities 手动遍历
    }
}
