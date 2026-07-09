package cn.dawnstring.fatality.utils;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

/**
 * 粒子效果预设
 *
 * <p>文件结构：预设效果 → 形状方法 → 底层基础方法
 *
 * <p>参数约定：
 * <ul>
 *   <li><b>spread</b> — 粒子在对应轴上的最大偏移范围（格）。例如 spreadX=2.0 表示粒子在 X 轴 [-1.0, +1.0] 格内随机散布。</li>
 *   <li><b>speed</b> — 粒子初速度（格/ tick）。1 tick = 0.05秒，speed=0.1 ≈ 2 格/秒。
 *       常用参考值：0 = 静止, 0.05 = 缓慢飘浮, 0.2 = 快速迸射, 0.5 = 爆炸冲击。</li>
 *   <li><b>radius</b> — 环形/球形的半径（格）。</li>
 *   <li><b>count</b> — 粒子总数。</li>
 *   <li><b>spacing</b> — 直线粒子间的间隔（格）。</li>
 *   <li><b>dx, dy, dz</b> — 服务端方法中同 spread，控制粒子散布范围（格）。</li>
 * </ul>
 */
public class ParticleUtil
{
    private static final Random RANDOM = new Random();

    public static void heal(Level level, Entity entity)
    {
        spawnParticlesAroundEntity(level, ParticleTypes.HEART, entity, 5, 1, 0);
    }

    public static void flame(Level level, double x, double y, double z, int count, double spread)
    {
        spawnParticles(level, ParticleTypes.FLAME, x, y, z, count, spread, spread, spread, 0.05);
    }

    public static void flame(Level level, Entity entity, int count)
    {
        spawnParticlesAroundEntity(level, ParticleTypes.FLAME, entity, count, 0.5, 0.05);
    }

    public static void flameExplosion(Level level, double x, double y, double z)
    {
        spawnParticles(level, ParticleTypes.FLAME, x, y, z, 30, 2, 2, 2, 0.3);
        spawnParticles(level, ParticleTypes.LAVA, x, y, z, 10, 1, 1, 1, 0.1);
        spawnRingParticles(level, ParticleTypes.SMALL_FLAME, x, y, z, 2, 20, 0.2);
    }

    public static void magic(Level level, Entity entity)
    {
        spawnParticlesAroundEntity(level, ParticleTypes.ENCHANTED_HIT, entity, 10, 1, 0.5);
    }

    public static void crit(Level level, Entity target, int count)
    {
        spawnParticlesAroundEntity(level, ParticleTypes.CRIT, target, count, 0.5, 0.2);
    }

    public static void teleport(Level level, double x, double y, double z)
    {
        spawnParticles(level, ParticleTypes.PORTAL, x, y, z, 30, 0.5, 0.5, 0.5, 0.5);
        spawnRingParticles(level, ParticleTypes.PORTAL, x, y, z, 1, 20, 0.2);
    }

    public static void explosion(Level level, double x, double y, double z)
    {
        spawnParticles(level, ParticleTypes.EXPLOSION, x, y, z, 10, 2, 2, 2, 0.5);
    }

    public static void lightning(Level level, double x, double y, double z, int count)
    {
        spawnParticles(level, ParticleTypes.ELECTRIC_SPARK, x, y, z, count, 1, 1, 1, 0.5);
    }

    public static void lightning(Level level, Entity entity, int count)
    {
        spawnParticlesAroundEntity(level, ParticleTypes.ELECTRIC_SPARK, entity, count, 0.8, 0.3);
    }

    public static void soul(Level level, double x, double y, double z, int count)
    {
        spawnParticles(level, ParticleTypes.SOUL, x, y, z, count, 0.5, 0.5, 0.5, 0.1);
    }

    public static void smoke(Level level, Entity entity, int count)
    {
        spawnParticlesAroundEntity(level, ParticleTypes.CAMPFIRE_COSY_SMOKE, entity, count, 0.6, 0.05);
    }

    public static void frost(Level level, Entity entity, int count)
    {
        spawnParticlesAroundEntity(level, ParticleTypes.SNOWFLAKE, entity, count, 1.2, 0.1);
    }

    public static void voidRift(Level level, Entity entity)
    {
        spawnHelixParticles(level, ParticleTypes.REVERSE_PORTAL, entity, 0.5, 30, 0.15);
        spawnParticlesAroundEntity(level, ParticleTypes.PORTAL, entity, 20, 1, 0.3);
    }

    public static void totem(Level level, Entity entity)
    {
        spawnParticlesAroundEntity(level, ParticleTypes.TOTEM_OF_UNDYING, entity, 15, 1, 0.3);
    }

    public static void enchant(Level level, Entity entity)
    {
        spawnParticlesAroundEntity(level, ParticleTypes.ENCHANT, entity, 12, 0.6, 0.15);
    }

    //形状预设

    //直线

    public static void spawnLineParticles(Level level, ParticleOptions particle, Vec3 from, Vec3 to, double spacing, double speed)
    {
        if (!level.isClientSide) return;
        double distance = from.distanceTo(to);
        int count = Math.max(1, (int) (distance / spacing));
        Vec3 step = to.subtract(from).normalize().scale(spacing);
        for (int i = 0; i <= count; i++)
        {
            Vec3 pos = from.add(step.scale(i));
            level.addParticle(particle, pos.x, pos.y, pos.z,
                    (RANDOM.nextDouble() - 0.5) * speed,
                    (RANDOM.nextDouble() - 0.5) * speed,
                    (RANDOM.nextDouble() - 0.5) * speed);
        }
    }

    public static void spawnLineParticles(Level level, ParticleOptions particle, Entity from, Entity to, double spacing, double speed)
    {
        spawnLineParticles(level, particle, from.position(), to.position(), spacing, speed);
    }

    public static void sendLineParticles(ServerLevel level, ParticleOptions particle, Vec3 from, Vec3 to, double spacing)
    {
        double distance = from.distanceTo(to);
        int count = Math.max(1, (int) (distance / spacing));
        Vec3 step = to.subtract(from).normalize().scale(spacing);
        for (int i = 0; i <= count; i++)
        {
            Vec3 pos = from.add(step.scale(i));
            level.sendParticles(particle, pos.x, pos.y, pos.z, 1, 0, 0, 0, 0);
        }
    }

    public static void sendLineParticles(ServerLevel level, ParticleOptions particle, Entity from, Entity to, double spacing)
    {
        sendLineParticles(level, particle, from.position(), to.position(), spacing);
    }

    //环形

    public static void spawnRingParticles(Level level, ParticleOptions particle, double x, double y, double z, double radius, int count, double speed)
    {
        if (!level.isClientSide) return;
        for (int i = 0; i < count; i++)
        {
            double angle = 2 * Math.PI * i / count;
            double px = x + radius * Math.cos(angle);
            double pz = z + radius * Math.sin(angle);
            level.addParticle(particle, px, y, pz,
                    Math.cos(angle) * speed, 0, Math.sin(angle) * speed);
        }
    }

    public static void spawnRingParticles(Level level, ParticleOptions particle, Entity entity, double radius, int count, double speed)
    {
        spawnRingParticles(level, particle,
                entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(),
                radius, count, speed);
    }

    public static void sendRingParticles(ServerLevel level, ParticleOptions particle, double x, double y, double z, double radius, int count)
    {
        for (int i = 0; i < count; i++)
        {
            double angle = 2 * Math.PI * i / count;
            double px = x + radius * Math.cos(angle);
            double pz = z + radius * Math.sin(angle);
            level.sendParticles(particle, px, y, pz, 1, 0, 0, 0, 0);
        }
    }

    public static void sendRingParticles(ServerLevel level, ParticleOptions particle, Entity entity, double radius, int count)
    {
        sendRingParticles(level, particle,
                entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(),
                radius, count);
    }

    //球体

    public static void spawnSphereParticles(Level level, ParticleOptions particle, double x, double y, double z, double radius, int count, double speed)
    {
        if (!level.isClientSide) return;
        for (int i = 0; i < count; i++)
        {
            double theta = 2 * Math.PI * RANDOM.nextDouble();
            double phi = Math.acos(2 * RANDOM.nextDouble() - 1);
            double px = x + radius * Math.sin(phi) * Math.cos(theta);
            double py = y + radius * Math.sin(phi) * Math.sin(theta);
            double pz = z + radius * Math.cos(phi);
            level.addParticle(particle, px, py, pz,
                    (RANDOM.nextDouble() - 0.5) * speed,
                    (RANDOM.nextDouble() - 0.5) * speed,
                    (RANDOM.nextDouble() - 0.5) * speed);
        }
    }

    public static void spawnSphereParticles(Level level, ParticleOptions particle, Entity entity, double radius, int count, double speed)
    {
        spawnSphereParticles(level, particle,
                entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(),
                radius, count, speed);
    }

    public static void sendSphereParticles(ServerLevel level, ParticleOptions particle, double x, double y, double z, double radius, int count)
    {
        for (int i = 0; i < count; i++)
        {
            double theta = 2 * Math.PI * RANDOM.nextDouble();
            double phi = Math.acos(2 * RANDOM.nextDouble() - 1);
            double px = x + radius * Math.sin(phi) * Math.cos(theta);
            double py = y + radius * Math.sin(phi) * Math.sin(theta);
            double pz = z + radius * Math.cos(phi);
            level.sendParticles(particle, px, py, pz, 1, 0, 0, 0, 0);
        }
    }

    public static void sendSphereParticles(ServerLevel level, ParticleOptions particle, Entity entity, double radius, int count)
    {
        sendSphereParticles(level, particle,
                entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(),
                radius, count);
    }

    //螺旋

    public static void spawnHelixParticles(Level level, ParticleOptions particle, double x, double y, double z, double radius, double height, int total, double speed)
    {
        if (!level.isClientSide) return;
        for (int i = 0; i < total; i++)
        {
            double progress = (double) i / total;
            double angle = progress * 2 * Math.PI * 4;
            double px = x + radius * Math.cos(angle);
            double py = y + progress * height;
            double pz = z + radius * Math.sin(angle);
            level.addParticle(particle, px, py, pz,
                    Math.cos(angle) * speed, 0.1, Math.sin(angle) * speed);
        }
    }

    public static void spawnHelixParticles(Level level, ParticleOptions particle, Entity entity, double radius, int total, double speed)
    {
        spawnHelixParticles(level, particle,
                entity.getX(), entity.getY(), entity.getZ(),
                radius, entity.getBbHeight(), total, speed);
    }

    public static void sendHelixParticles(ServerLevel level, ParticleOptions particle, double x, double y, double z, double radius, double height, int total)
    {
        for (int i = 0; i < total; i++)
        {
            double progress = (double) i / total;
            double angle = progress * 2 * Math.PI * 4;
            double px = x + radius * Math.cos(angle);
            double py = y + progress * height;
            double pz = z + radius * Math.sin(angle);
            level.sendParticles(particle, px, py, pz, 1, 0, 0, 0, 0);
        }
    }

    public static void sendHelixParticles(ServerLevel level, ParticleOptions particle, Entity entity, double radius, int total)
    {
        sendHelixParticles(level, particle,
                entity.getX(), entity.getY(), entity.getZ(),
                radius, entity.getBbHeight(), total);
    }

    //底层方法

    public static void spawnParticle(Level level, ParticleOptions particle, double x, double y, double z, double vx, double vy, double vz)
    {
        if (level.isClientSide)
            level.addParticle(particle, x, y, z, vx, vy, vz);
    }

    public static void spawnParticles(Level level, ParticleOptions particle, double x, double y, double z, int count, double spreadX, double spreadY, double spreadZ, double speed)
    {
        if (!level.isClientSide) return;
        for (int i = 0; i < count; i++)
            level.addParticle(particle,
                    x + (RANDOM.nextDouble() - 0.5) * spreadX,
                    y + (RANDOM.nextDouble() - 0.5) * spreadY,
                    z + (RANDOM.nextDouble() - 0.5) * spreadZ,
                    (RANDOM.nextDouble() - 0.5) * speed,
                    (RANDOM.nextDouble() - 0.5) * speed,
                    (RANDOM.nextDouble() - 0.5) * speed);
    }

    public static void spawnParticlesAroundEntity(Level level, ParticleOptions particle, Entity entity, int count, double spread, double speed)
    {
        spawnParticles(level, particle,
                entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(),
                count, spread, spread, spread, speed);
    }

    public static void sendParticles(ServerLevel level, ParticleOptions particle, double x, double y, double z, int count, double dx, double dy, double dz, double speed)
    {
        level.sendParticles(particle, x, y, z, count, dx, dy, dz, speed);
    }

    public static void sendParticlesAroundEntity(ServerLevel level, ParticleOptions particle, Entity entity, int count, double spread, double speed)
    {
        sendParticles(level, particle,
                entity.getX(), entity.getY() + entity.getBbHeight() / 2, entity.getZ(),
                count, spread, spread, spread, speed);
    }
}
