package cn.dawnstring.fatality.core.register;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.core.ability.AbilitySystem;
import cn.dawnstring.fatality.core.accessory.AccessoryManager;
import cn.dawnstring.fatality.core.capability.PlayerAttributesProvider;
import cn.dawnstring.fatality.core.input.PlayerInputState;
import cn.dawnstring.fatality.core.network.SyncAttributesPacket;
import cn.dawnstring.fatality.item.BaseShieldItem;
import cn.dawnstring.fatality.item.BaseWingItem;
import cn.dawnstring.fatality.utils.KeyUtil;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
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
            AccessoryManager.reload(player);
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

    //玩家退出服务器时保存饰品数据
    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event)
    {
        if (event.getEntity() instanceof ServerPlayer player)
        {
            AccessoryManager.save(player);
            AccessoryManager.remove(player.getUUID());
            PlayerAttributesProvider.remove(player.getUUID());
            PlayerInputState.remove(player.getUUID());
            BaseWingItem.remove(player.getUUID());
            BaseShieldItem.remove(player.getUUID());
            KeyUtil.reset(player.getUUID());
        }
    }

    //服务器关闭时保存玩家饰品数据
    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event)
    {
        var players = event.getServer().getPlayerList().getPlayers();
        for (ServerPlayer player : players)
        {
            AccessoryManager.save(player);
            AccessoryManager.remove(player.getUUID());
            PlayerAttributesProvider.remove(player.getUUID());
            PlayerInputState.remove(player.getUUID());
            BaseWingItem.remove(player.getUUID());
            BaseShieldItem.remove(player.getUUID());
            KeyUtil.reset(player.getUUID());
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
        if (event.getEntity() instanceof ServerPlayer player)
        {
            if (AccessoryManager.onAttacked(player, event.getSource(), event.getAmount()))
            {
                event.setCanceled(true);
                return;
            }

            float modified = AbilitySystem.onHurt(player, event.getSource(), event.getAmount());
            if (modified <= 0)
                event.setCanceled(true);
            else if (modified != event.getAmount())
                event.setAmount(modified);
        }

        if (event.getSource().getEntity() instanceof ServerPlayer attacker && event.getEntity() != attacker)
        {
            float modified = AbilitySystem.modifyOutgoingDamage(attacker, event.getEntity(), event.getAmount());
            if (modified != event.getAmount())
                event.setAmount(modified);
            if (modified > 0)
                AbilitySystem.onHit(attacker, event.getEntity(), modified);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event)
    {
        if (event.getSource().getEntity() instanceof ServerPlayer player)
            AbilitySystem.onKill(player, event.getEntity());
    }
}
