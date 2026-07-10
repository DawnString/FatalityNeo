package cn.dawnstring.fatality.utils;

import cn.dawnstring.fatality.core.input.PlayerInputState;
import cn.dawnstring.fatality.mixin.LivingEntityAccessor;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 按键检测工具
 * <p>
 * 服务端通过 ClientInputHandler → PlayerInputPayload → PlayerInputState 管道获取输入
 * 客户端直接从 LivingEntity.xxa/zza/jumping 读取
 */
public class KeyUtil
{
    private static final Map<UUID, Map<String, Integer>> lastPressTick = new ConcurrentHashMap<>();
    private static final int DOUBLE_TAP_INTERVAL = 10;

    public static final String KEY_JUMP = "jump";
    public static final String KEY_SNEAK = "sneak";

    /**
     * 检测玩家是否按住跳跃键
     */
    public static boolean isJumping(Player player)
    {
        if (!player.level().isClientSide())
            return PlayerInputState.isJumping(player);
        return ((LivingEntityAccessor) player).isJumping();
    }

    /**
     * 检测玩家是否在上升（motion 方案，不依赖网络同步）
     */
    public static boolean isAscending(Player player)
    {
        return !player.onGround() && player.getDeltaMovement().y > 0.08;
    }

    /**
     * 检测玩家是否按住潜行键
     */
    public static boolean isSneaking(Player player)
    {
        return player.isShiftKeyDown();
    }

    public static boolean isForward(Player player)
    {
        if (!player.level().isClientSide())
            return PlayerInputState.getForwardImpulse(player) > 0;
        return player.zza > 0;
    }

    public static boolean isBackward(Player player)
    {
        if (!player.level().isClientSide())
            return PlayerInputState.getForwardImpulse(player) < 0;
        return player.zza < 0;
    }

    public static boolean isLeft(Player player)
    {
        if (!player.level().isClientSide())
            return PlayerInputState.getLeftImpulse(player) > 0;
        return player.xxa < 0;
    }

    public static boolean isRight(Player player)
    {
        if (!player.level().isClientSide())
            return PlayerInputState.getLeftImpulse(player) < 0;
        return player.xxa > 0;
    }

    public static boolean isMoving(Player player)
    {
        if (!player.level().isClientSide())
            return PlayerInputState.getForwardImpulse(player) != 0
                    || PlayerInputState.getLeftImpulse(player) != 0;
        return player.xxa != 0 || player.zza != 0;
    }

    /**
     * 双击检测
     * 在按键按下的那一个 tick 调用（上升沿），重复调用会导致误触
     */
    public static boolean consumeDoubleTap(Player player, String key)
    {
        Map<String, Integer> map = lastPressTick.computeIfAbsent(player.getUUID(), k -> new ConcurrentHashMap<>());
        int now = player.tickCount;
        Integer last = map.get(key);
        map.put(key, now);

        if (last != null && now - last <= DOUBLE_TAP_INTERVAL)
        {
            map.remove(key);
            return true;
        }
        return false;
    }

    public static void reset(UUID uuid)
    {
        lastPressTick.remove(uuid);
    }
}
