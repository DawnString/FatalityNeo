package cn.dawnstring.fatality.item;

import cn.dawnstring.fatality.utils.KeyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BaseShieldItem extends AccessoryItem
{
    private static final Map<UUID, ShieldState> DASH_STATE = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> PROCESSED_TICK = new ConcurrentHashMap<>();
    private static final Map<UUID, BlockPos> LAST_DASH_POS = new ConcurrentHashMap<>();

    private final ShieldStats shieldStats;

    public BaseShieldItem(ShieldStats shieldStats, List<StatModifier> modifiers)
    {
        super(modifiers);
        this.shieldStats = shieldStats;
    }

    public ShieldStats getShieldStats()
    {
        return shieldStats;
    }

    @Override
    public void tick(Player player)
    {
        super.tick(player);

        if (player.level().isClientSide())
        {
            tickClient(player);
            return;
        }

        // 双击方向键检测（每 tick 无条件执行以更新上升沿）
        if (KeyUtil.consumeDoubleTapW(player))
            startDash(player, Direction.FORWARD);

        if (KeyUtil.consumeDoubleTapS(player))
            startDash(player, Direction.BACKWARD);

        if (KeyUtil.consumeDoubleTapA(player))
            startDash(player, Direction.LEFT);

        if (KeyUtil.consumeDoubleTapD(player))
            startDash(player, Direction.RIGHT);

        tickShieldState(player);
    }

    private void tickClient(Player player) {}

    private void startDash(Player player, Direction direction)
    {
        UUID uuid = player.getUUID();

        ShieldState state = DASH_STATE.get(uuid);
        if (state != null && state.mode() != DashMode.IDLE) return;

        if (player.isSpectator()) return;

        // 锁定冲刺方向（防止冲刺中转身改变方向）
        Vec3 dashVec = switch (direction)
        {
            case FORWARD  -> Vec3.directionFromRotation(0, player.getYRot());
            case BACKWARD -> Vec3.directionFromRotation(0, player.getYRot() + 180);
            case LEFT     -> Vec3.directionFromRotation(0, player.getYRot() - 90);
            case RIGHT    -> Vec3.directionFromRotation(0, player.getYRot() + 90);
        };
        Vec3 velocity = dashVec.scale(shieldStats.dashSpeed());

        player.setDeltaMovement(velocity);
        player.hurtMarked = true;
        player.fallDistance = 0;

        DASH_STATE.put(uuid, new ShieldState(
                DashMode.DASHING, direction, 0, new HashSet<>(), velocity, 0
        ));
    }

    private void tickShieldState(Player player)
    {
        UUID uuid = player.getUUID();
        ShieldState state = DASH_STATE.get(uuid);
        if (state == null || state.mode() == DashMode.IDLE) return;

        if (!player.isAlive())
        {
            DASH_STATE.remove(uuid);
            LAST_DASH_POS.remove(uuid);
            return;
        }

        // 去重
        Integer lastTick = PROCESSED_TICK.get(uuid);
        if (lastTick != null && lastTick == player.tickCount) return;
        PROCESSED_TICK.put(uuid, player.tickCount);

        switch (state.mode())
        {
            case DASHING  -> tickDashing(player, state);
            case COOLDOWN -> tickCooldown(player, state);
        }
    }

    private void tickDashing(Player player, ShieldState state)
    {
        UUID uuid = player.getUUID();
        int newTimer = state.timer() + 1;

        // 维持冲刺速度
        player.setDeltaMovement(state.dashVec());
        player.hurtMarked = true;
        player.fallDistance = 0;

        // 卡墙检测
        BlockPos currentPos = player.blockPosition();
        BlockPos lastPos = LAST_DASH_POS.put(uuid, currentPos);
        int stuck = (lastPos != null && currentPos.equals(lastPos)) ? state.stuckTicks() + 1 : 0;

        if (stuck >= 2 || newTimer >= shieldStats.dashDuration())
        {
            DASH_STATE.put(uuid, new ShieldState(
                    DashMode.COOLDOWN, state.dir(), 0, state.hitEntities(), Vec3.ZERO, 0
            ));
            LAST_DASH_POS.remove(uuid);
            return;
        }

        // 更新状态
        DASH_STATE.put(uuid, new ShieldState(
                DashMode.DASHING, state.dir(), newTimer, state.hitEntities(), state.dashVec(), stuck
        ));

        // 碰撞检测
        AABB aabb = player.getBoundingBox();
        List<LivingEntity> targets = player.level().getEntitiesOfClass(
                LivingEntity.class, aabb, e -> e != player && e.isAlive()
        );
        if (targets.isEmpty()) return;

        for (LivingEntity target : targets)
        {
            UUID targetId = target.getUUID();

            if (shieldStats.type() == ShieldType.PHASE && state.hitEntities().contains(targetId))
                continue;

            // 造成伤害
            target.hurt(player.damageSources().playerAttack(player), (float) shieldStats.damageOnHit());

            if (shieldStats.type() == ShieldType.KNOCKBACK)
            {
                // 击退实体
                Vec3 away = target.position().subtract(player.position());
                away = new Vec3(away.x, 0, away.z).normalize();

                target.setDeltaMovement(away.scale(shieldStats.knockbackStrength()).add(0, 0.3, 0));
                target.hurtMarked = true;

                // 玩家反冲
                player.setDeltaMovement(away.scale(-shieldStats.knockbackStrength() * 0.8));
                player.hurtMarked = true;

                DASH_STATE.put(uuid, new ShieldState(
                        DashMode.COOLDOWN, state.dir(), 0, state.hitEntities(), Vec3.ZERO, 0
                ));
                LAST_DASH_POS.remove(uuid);
                return;
            }
            else
            {
                // PHASE：标记已穿透
                state.hitEntities().add(targetId);
            }
        }
    }

    private void tickCooldown(Player player, ShieldState state)
    {
        int newTimer = state.timer() + 1;
        if (newTimer >= shieldStats.cooldown())
        {
            DASH_STATE.remove(player.getUUID());
            LAST_DASH_POS.remove(player.getUUID());
        }
        else
        {
            DASH_STATE.put(player.getUUID(), new ShieldState(
                    DashMode.COOLDOWN, state.dir(), newTimer, state.hitEntities(), Vec3.ZERO, 0
            ));
        }
    }

    public static void remove(UUID uuid)
    {
        DASH_STATE.remove(uuid);
        PROCESSED_TICK.remove(uuid);
        LAST_DASH_POS.remove(uuid);
    }
}

/**
 * @param mode       IDLE / DASHING / COOLDOWN
 * @param dir        FORWARD / BACKWARD / LEFT / RIGHT
 * @param timer      dash 或 cooldown 的计时
 * @param hitEntities PHASE 型已穿透的实体
 * @param dashVec    锁定的冲刺速度向量
 * @param stuckTicks 卡墙连续 tick 数
 */
record ShieldState(
        DashMode mode,
        Direction dir,
        int timer,
        Set<UUID> hitEntities,
        Vec3 dashVec,
        int stuckTicks
) {}

enum DashMode { IDLE, DASHING, COOLDOWN }
enum Direction { FORWARD, BACKWARD, LEFT, RIGHT }
