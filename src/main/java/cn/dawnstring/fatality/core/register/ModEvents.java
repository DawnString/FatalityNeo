package cn.dawnstring.fatality.core.register;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.core.capability.PlayerAttributesProvider;
import cn.dawnstring.fatality.core.network.SyncAttributesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = Fatality.MODID)
public class ModEvents
{
    //玩家登入
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer player)
        {
            PacketDistributor.sendToPlayer(
                    player,
                    new SyncAttributesPacket(PlayerAttributesProvider.getAttributes(player))
            );
        }
    }

    //玩家重生
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerRespawnEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer player)
        {
            PacketDistributor.sendToPlayer(
                    player,
                    new SyncAttributesPacket(PlayerAttributesProvider.getAttributes(player))
            );
        }
    }

    //玩家跨纬度
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer player)
        {
            PacketDistributor.sendToPlayer(
                    player,
                    new SyncAttributesPacket(PlayerAttributesProvider.getAttributes(player))
            );
        }
    }
}
