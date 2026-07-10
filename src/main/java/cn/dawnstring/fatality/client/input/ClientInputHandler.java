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

/**
 * 客户端每 tick 发输入状态到服务端
 */
@EventBusSubscriber(modid = Fatality.MODID, value = Dist.CLIENT)
public class ClientInputHandler
{
    @SubscribeEvent
    public static void onClientDisconnect(ClientPlayerNetworkEvent.LoggingOut event)
    {
        PlayerEffectUtil.clear();
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event)
    {
        var player = Minecraft.getInstance().player;
        if (player == null) return;

        PacketDistributor.sendToServer(new PlayerInputPayload(
                player.input.jumping,
                player.input.shiftKeyDown,
                player.input.forwardImpulse,
                player.input.leftImpulse
        ));
    }
}
