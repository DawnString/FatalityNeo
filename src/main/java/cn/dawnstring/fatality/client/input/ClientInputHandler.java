package cn.dawnstring.fatality.client.input;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.network.PlayerInputPayload;
import cn.dawnstring.fatality.utils.PlayerEffectUtil;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端每 tick 发输入状态到服务端<br>
 * 双 W/S/A/S 冲刺检测在客户端进行，精确捕捉 press-release-press 序列
 */
@EventBusSubscriber(modid = Fatality.MODID, value = Dist.CLIENT)
public class ClientInputHandler
{
    private static float prevForwardImpulse = 0;
    private static float prevLeftImpulse = 0;
    private static final Map<String, Integer> lastPressTick = new ConcurrentHashMap<>();
    private static final int DOUBLE_TAP_INTERVAL = 10;

    @SubscribeEvent
    public static void onClientDisconnect(ClientPlayerNetworkEvent.LoggingOut event)
    {
        PlayerEffectUtil.clear();
        reset();
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event)
    {
        var player = Minecraft.getInstance().player;
        if (player == null) return;

        // 客户端双击方向键检测（避免服务端网络包合并丢失中间状态）
        float forward = player.input.forwardImpulse;
        float left = player.input.leftImpulse;
        int tick = player.tickCount;

        int dashDirection = -1;

        if (forward > 0 && prevForwardImpulse <= 0 && isDoubleTap("key_w", tick))
            dashDirection = 0; // FORWARD

        if (forward < 0 && prevForwardImpulse >= 0 && isDoubleTap("key_s", tick))
            dashDirection = 1; // BACKWARD

        if (left > 0 && prevLeftImpulse <= 0 && isDoubleTap("key_a", tick))
            dashDirection = 2; // LEFT

        if (left < 0 && prevLeftImpulse >= 0 && isDoubleTap("key_d", tick))
            dashDirection = 3; // RIGHT

        prevForwardImpulse = forward;
        prevLeftImpulse = left;

        // 发送输入状态到服务端（内含冲刺标记）
        PacketDistributor.sendToServer(new PlayerInputPayload(
                player.input.jumping,
                player.input.shiftKeyDown,
                player.input.forwardImpulse,
                player.input.leftImpulse,
                dashDirection
        ));
    }

    private static boolean isDoubleTap(String key, int now)
    {
        Integer last = lastPressTick.get(key);
        lastPressTick.put(key, now);
        return last != null && now - last <= DOUBLE_TAP_INTERVAL;
    }

    private static void reset()
    {
        prevForwardImpulse = 0;
        prevLeftImpulse = 0;
        lastPressTick.clear();
    }
}
