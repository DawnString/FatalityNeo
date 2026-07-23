package cn.dawnstring.fatality.item.weapon.projectile.behavior;

import cn.dawnstring.fatality.item.weapon.projectile.ProjectileBehavior;
import cn.dawnstring.fatality.item.weapon.projectile.WeaponProjectile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class TrackingFlight implements ProjectileBehavior
{
    private final float range;
    private final float turnSpeed;
    private final int delayTicks;

    /**
     * @param range      追踪检测范围
     * @param turnSpeed  转向速度 (0~1), 越大转弯越急
     * @param delayTicks 延迟多少 tick 后开始追踪
     */
    public TrackingFlight(float range, float turnSpeed, int delayTicks)
    {
        this.range = range;
        this.turnSpeed = turnSpeed;
        this.delayTicks = delayTicks;
    }

    public TrackingFlight(float range, float turnSpeed)
    {
        this(range, turnSpeed, 0);
    }

    @Override
    public void onTick(WeaponProjectile projectile)
    {
        // 延迟阶段不追踪
        if (projectile.getAge() < delayTicks) return;

        var level = projectile.level();
        if (level.isClientSide()) return;

        Vec3 pos = projectile.position();
        Vec3 currentVel = projectile.getDeltaMovement();
        double speed = currentVel.length();

        // 寻找范围内最近的敌人
        AABB area = AABB.ofSize(pos, range * 2, range * 2, range * 2);
        LivingEntity target = null;
        double closestDist = Double.MAX_VALUE;

        for (LivingEntity e : level.getEntitiesOfClass(
                LivingEntity.class, area,
                e -> e != projectile.getOwner() && e.isAlive()))
        {
            double dist = e.distanceToSqr(pos);
            if (dist < closestDist)
            {
                closestDist = dist;
                target = e;
            }
        }

        if (target != null)
        {
            Vec3 toTarget = target.getEyePosition().subtract(pos).normalize();
            Vec3 currentDir = currentVel.normalize();

            // 平滑转向
            Vec3 newDir = currentDir.lerp(toTarget, turnSpeed).normalize();
            projectile.setDeltaMovement(newDir.scale(speed));
        }
    }
}
