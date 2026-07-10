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
    public static final String KEY_W = "key_w";
    public static final String KEY_S = "key_s";
    public static final String KEY_A = "key_a";
    public static final String KEY_D = "key_d";

    private static final Map<UUID, Float> PREV_FORWARD = new ConcurrentHashMap<>();
    private static final Map<UUID, Float> PREV_LEFT = new ConcurrentHashMap<>();

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
     * 双击W检测
     * @param player 玩家
     * @return 是否双双击W键
     */
    public static boolean consumeDoubleTapW(Player player)
    {
        UUID uuid = player.getUUID();
        float cur = PlayerInputState.getForwardImpulse(player);
        float prev = PREV_FORWARD.getOrDefault(uuid, 0f);
        PREV_FORWARD.put(uuid, cur);

        return cur > 0 && prev <= 0 && consumeDoubleTap(player, KEY_W);
    }

    /**
     * 双击S检测
     * @param player 玩家
     * @return 是否双击S键
     */
    public static boolean consumeDoubleTapS(Player player)
    {
        UUID uuid = player.getUUID();
        float cur = PlayerInputState.getForwardImpulse(player);
        float prev = PREV_FORWARD.getOrDefault(uuid, 0f);
        PREV_FORWARD.put(uuid, cur);

        return cur < 0 && prev >= 0 && consumeDoubleTap(player, KEY_S);
    }

    /**
     * 双击A检测
     * @param player 玩家
     * @return 是否双击A键
     */
    public static boolean consumeDoubleTapA(Player player)
    {
        UUID uuid = player.getUUID();
        float cur = PlayerInputState.getLeftImpulse(player);
        float prev = PREV_LEFT.getOrDefault(uuid, 0f);
        PREV_LEFT.put(uuid, cur);

        return cur > 0 && prev <= 0 && consumeDoubleTap(player, KEY_A);
    }

    /**
     * 双击D键检测
     * @param player 玩家
     * @return 是否双击D键
     */
    public static boolean consumeDoubleTapD(Player player)
    {
        UUID uuid = player.getUUID();
        float cur = PlayerInputState.getLeftImpulse(player);
        float prev = PREV_LEFT.getOrDefault(uuid, 0f);
        PREV_LEFT.put(uuid, cur);

        return cur < 0 && prev >= 0 && consumeDoubleTap(player, KEY_D);
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
        PREV_FORWARD.remove(uuid);
        PREV_LEFT.remove(uuid);
    }
}
