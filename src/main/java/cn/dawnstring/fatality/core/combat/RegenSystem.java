package cn.dawnstring.fatality.core.combat;

import cn.dawnstring.fatality.core.capability.PlayerAttributes;
import cn.dawnstring.fatality.core.capability.PlayerAttributesProvider;
import cn.dawnstring.fatality.core.network.SyncAttributesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RegenSystem
{
    private static final Map<UUID, RegenData> dataMap = new ConcurrentHashMap<>();
    private static final int REGEN_INTERVAL = 20;     // 1秒（20 tick）恢复一次
    private static final int PAUSE_TICKS = 60;         // 受击/消耗魔力暂停3秒
    private static final int BASE_MANA_REGEN = 10;     // 每秒基础魔力恢复

    private static class RegenData
    {
        int healthCooldown;
        int manaCooldown;
        int healthTickTimer;

        RegenData(int healthCooldown, int manaCooldown, int healthTickTimer)
        {
            this.healthCooldown = healthCooldown;
            this.manaCooldown = manaCooldown;
            this.healthTickTimer = healthTickTimer;
        }
    }

    public static void tick(Player player)
    {
        PlayerAttributes attrs = PlayerAttributesProvider.getAttributes(player);
        RegenData data = dataMap.computeIfAbsent(player.getUUID(),
                id -> new RegenData(0, 0, 0));

        // 递减冷却
        if (data.healthCooldown > 0)
            data.healthCooldown--;
        if (data.manaCooldown > 0)
            data.manaCooldown--;

        // 生命恢复（未受击时）
        if (data.healthCooldown <= 0)
        {
            data.healthTickTimer++;
            if (data.healthTickTimer >= REGEN_INTERVAL)
            {
                float amount = 1.0f + attrs.getRecoverHealthSpeedBonus();
                player.heal(amount);
                data.healthTickTimer = 0;
            }
        }

        // 魔力恢复（未消耗魔力时）
        if (data.manaCooldown <= 0 && player.tickCount % REGEN_INTERVAL == 0)
        {
            int regen = BASE_MANA_REGEN + (int) attrs.getRecoverManaSpeedBonus();
            attrs.addCurrentMana(regen);

            if (player instanceof ServerPlayer sp)
            {
                PacketDistributor.sendToPlayer(sp,
                        new SyncAttributesPacket(PlayerAttributesProvider.getAttributes(player)));
            }
        }
    }

    /**
     * 玩家受击时调用，暂停生命与魔力恢复 3 秒。
     */
    public static void onHurt(Player player)
    {
        RegenData data = dataMap.computeIfAbsent(player.getUUID(),
                id -> new RegenData(0, 0, 0));
        data.healthCooldown = PAUSE_TICKS;
        data.manaCooldown = PAUSE_TICKS;
        data.healthTickTimer = 0;
    }

    /**
     * 消耗魔力，成功返回 true，魔力不足返回 false。
     * 消耗魔力会暂停魔力恢复 3 秒。
     */
    public static boolean consumeMana(Player player, int amount)
    {
        PlayerAttributes attrs = PlayerAttributesProvider.getAttributes(player);
        if (attrs.getCurrentMana() < amount)
            return false;

        attrs.setCurrentMana(attrs.getCurrentMana() - amount);
        RegenData data = dataMap.computeIfAbsent(player.getUUID(),
                id -> new RegenData(0, 0, 0));
        data.manaCooldown = PAUSE_TICKS;

        // 同步魔力到客户端
        if (player instanceof ServerPlayer sp)
        {
            PacketDistributor.sendToPlayer(sp,
                    new SyncAttributesPacket(PlayerAttributesProvider.getAttributes(player)));
        }

        return true;
    }

    public static int getMana(Player player)
    {
        return PlayerAttributesProvider.getAttributes(player).getCurrentMana();
    }

    public static int getMaxMana(Player player)
    {
        return PlayerAttributesProvider.getAttributes(player).getMaxMana();
    }

    public static void remove(UUID uuid)
    {
        dataMap.remove(uuid);
    }
}
