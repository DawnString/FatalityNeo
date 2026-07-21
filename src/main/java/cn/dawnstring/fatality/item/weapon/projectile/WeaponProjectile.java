package cn.dawnstring.fatality.item.weapon.projectile;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import net.minecraft.world.level.ClipContext;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class WeaponProjectile extends Projectile
{
    private static final EntityDataAccessor<String> TEXTURE_ID =
            SynchedEntityData.defineId(WeaponProjectile.class, EntityDataSerializers.STRING);

    //弹幕参数
    private ProjectileStats stats;
    //行为组件列表
    private List<ProjectileBehavior> behaviors;
    //方向
    private Vec3 direction;
    //存活tick数
    private int age;
    //已穿透数
    private int pierceCount;
    //已弹射数
    private int bounceCount;
    //已命中实体(穿透)
    private final Set<UUID> hitEntities = ConcurrentHashMap.newKeySet();

    // 服务端构造
    public WeaponProjectile(EntityType<? extends Projectile> type, Level level,
                            Player owner, Vec3 direction,
                            ProjectileStats stats, List<ProjectileBehavior> behaviors)
    {
        super(type, level);
        setOwner(owner);
        this.direction = direction.normalize();
        this.stats = stats;
        this.behaviors = behaviors;
        this.age = 0;

        setPos(owner.getX(), owner.getEyeY() - 0.3, owner.getZ());
        setDeltaMovement(this.direction.scale(stats.speed()));
        entityData.set(TEXTURE_ID, stats.textureId());
    }

    // 客户端构造 (网络包还原)
    public WeaponProjectile(EntityType<? extends Projectile> type, Level level)
    {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder)
    {
        builder.define(TEXTURE_ID, "");
    }

    @Override
    public void tick()
    {
        super.tick();

        if (!level().isClientSide())
            tickServer();
        else
            tickClient();
    }

    private void tickServer()
    {
        age++;

        // 超时销毁
        if (age > stats.lifetime())
        {
            discard();
            return;
        }

        // 应用重力
        if (stats.gravity())
            setDeltaMovement(getDeltaMovement().add(0, -0.03, 0));

        // 行为 onTick
        for (ProjectileBehavior b : behaviors)
            b.onTick(this);

        // 实体碰撞检测
        Vec3 pos = position();
        Vec3 nextPos = pos.add(getDeltaMovement());
        AABB moveBox = getBoundingBox().expandTowards(getDeltaMovement()).inflate(1.0);

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                this, pos, nextPos, moveBox,
                e -> e != getOwner() && e.isAlive() && !hitEntities.contains(e.getUUID()),
                stats.size()
        );

        if (entityHit != null)
        {
            onHitEntity(entityHit);
            return; // 如果穿透则在 onHitEntity 中继续移动
        }

        // 方块碰撞检测
        Vec3 start = position();
        Vec3 end = start.add(getDeltaMovement());
        BlockHitResult blockHit = level().clip(new ClipContext(
                start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this
        ));
        if (blockHit.getType() != net.minecraft.world.phys.HitResult.Type.MISS)
        {
            onHitBlock(blockHit);
        }

        // 移动
        setPos(nextPos);
    }

    private void tickClient()
    {
        age++;
        if (stats != null ? age > stats.lifetime() : age > 200)
            discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult hit)
    {
        Entity target = hit.getEntity();
        UUID targetId = target.getUUID();

        if (hitEntities.contains(targetId)) return;
        hitEntities.add(targetId);

        var ctx = new ProjectileBehavior.ProjectileHitContext(this, target, hit);

        for (ProjectileBehavior b : behaviors)
            b.onHitEntity(ctx);

        // 伤害
        if (target instanceof LivingEntity living && stats != null)
        {
            Entity owner = getOwner();
            var source = owner instanceof Player p
                    ? p.damageSources().playerAttack(p)
                    : living.damageSources().generic();

            living.hurt(source, stats.damage());
        }

        // 穿透逻辑
        pierceCount++;
        if (pierceCount >= stats.maxPierce() && stats.maxPierce() >= 0)
            discard();
    }

    protected void onHitBlock(BlockHitResult hit)
    {
        var ctx = new ProjectileBehavior.ProjectileHitContext(this, null, hit);

        for (ProjectileBehavior b : behaviors)
            b.onHitBlock(ctx);

        bounceCount++;
        if (bounceCount >= stats.maxBounce() && stats.maxBounce() >= 0)
            discard();
        else
            bounce(hit);
    }

    private void bounce(BlockHitResult hit)
    {
        Vec3 normal = Vec3.atLowerCornerOf(hit.getDirection().getNormal());
        direction = direction.subtract(normal.scale(2 * direction.dot(normal))).normalize();
        setDeltaMovement(direction.scale(stats.speed()));
    }

    public String getTextureId()
    {
        return entityData.get(TEXTURE_ID);
    }

    public ProjectileStats getStats()
    {
        return stats;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag)
    {
        // TODO: NBT 序列化（支持持久化）
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag)
    {
        // TODO: NBT 序列化
    }
}
