package cn.dawnstring.fatality.core.input;

import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务端存储，UUID → InputSnapshot
 */
public class PlayerInputState
{
    private static final Map<UUID, Snapshot> INPUT_MAP = new ConcurrentHashMap<>();
    private static final Snapshot EMPTY = new Snapshot(false, false, 0, 0);

    public record Snapshot(boolean jumping, boolean sneaking, float forwardImpulse, float leftImpulse) {}

    public static void update(UUID uuid, boolean jumping, boolean sneaking, float forwardImpulse, float leftImpulse)
    {
        if (jumping == EMPTY.jumping && sneaking == EMPTY.sneaking
                && forwardImpulse == EMPTY.forwardImpulse && leftImpulse == EMPTY.leftImpulse)
        {
            INPUT_MAP.remove(uuid);
        }
        else
        {
            INPUT_MAP.put(uuid, new Snapshot(jumping, sneaking, forwardImpulse, leftImpulse));
        }
    }

    public static Snapshot get(Player player)
    {
        return INPUT_MAP.getOrDefault(player.getUUID(), EMPTY);
    }

    public static boolean isJumping(Player player)
    {
        return get(player).jumping();
    }

    public static boolean isSneaking(Player player)
    {
        return get(player).sneaking();
    }

    public static float getForwardImpulse(Player player)
    {
        return get(player).forwardImpulse();
    }

    public static float getLeftImpulse(Player player)
    {
        return get(player).leftImpulse();
    }

    public static void remove(UUID uuid)
    {
        INPUT_MAP.remove(uuid);
    }
}
