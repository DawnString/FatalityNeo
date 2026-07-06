package cn.dawnstring.fatality.core.register;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.core.accessory.AccessoryManager;
import cn.dawnstring.fatality.core.capability.PlayerAttributesProvider;
import cn.dawnstring.fatality.core.network.SyncAttributesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = Fatality.MODID)
public class ModEvents
{
    //玩家登入
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer player)
        {
            AccessoryManager.refreshAttributes(player);
            PacketDistributor.sendToPlayer(player,
                    new SyncAttributesPacket(PlayerAttributesProvider.getAttributes(player)));
        }
    }

    //玩家重生
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer player)
            PacketDistributor.sendToPlayer(player,
                    new SyncAttributesPacket(PlayerAttributesProvider.getAttributes(player)));
    }

    //玩家跨纬度
    @SubscribeEvent
    public static void onPlayerChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer player)
            PacketDistributor.sendToPlayer(player,
                    new SyncAttributesPacket(PlayerAttributesProvider.getAttributes(player)));
    }

    //玩家退出时保存饰品数据
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer player)
        {
            AccessoryManager.save(player);
            AccessoryManager.remove(player.getUUID());
            PlayerAttributesProvider.remove(player.getUUID());
        }
    }

    //玩家tick
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event)
    {
        PlayerAttributesProvider.getAttributes(event.getEntity()).tick();
        AccessoryManager.tick(event.getEntity());
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingIncomingDamageEvent event)
    {
        boolean r = false;
        if (event.getEntity() instanceof ServerPlayer player)
        {
            r = AccessoryManager.onAttacked(player, event.getSource(), event.getAmount());
        }

        event.setCanceled(r);
    }
}
