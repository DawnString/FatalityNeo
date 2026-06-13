package cn.dawnstring.fatality.core.register;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.core.network.SyncAttributesPacket;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = Fatality.MODID)
public class ModNetwork
{
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event)
    {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                SyncAttributesPacket.TYPE,
                SyncAttributesPacket.STREAM_CODEC,
                (packet, context) -> context.enqueueWork(() -> {
                    var player = Minecraft.getInstance().player;
                    if (player != null)
                    {
                        var attrs = player.getCapability(ModCapabilities.PLAYER_ATTRIBUTES);
                        if (attrs != null) attrs.copyFrom(packet.attributes());
                    }
                })
        );
    }
}
