package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "memory_bulwark", category = ItemCategory.ACCESSORY)
public class MemoryBulwark extends AccessoryItem implements Ability
{
    private record StoredData(float amount, long storeTick) {}

    private final Map<UUID, StoredData> damageMap = new ConcurrentHashMap<>();

    public MemoryBulwark()
    {
        super(List.of());
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        StoredData stored = damageMap.get(player.getUUID());

        if (stored == null)
        {
            // 首次受伤：存储伤害，本次免疫
            damageMap.put(player.getUUID(), new StoredData(amount, player.tickCount));
            return 0;
        }
        else
        {
            // 二次受伤：存储伤害无视护甲直接扣血
            damageMap.remove(player.getUUID());
            player.setHealth(Math.max(0, player.getHealth() - stored.amount));
            return amount;
        }
    }

    @Override
    public void tick(Player player)
    {
        StoredData stored = damageMap.get(player.getUUID());
        if (stored != null && player.tickCount - stored.storeTick >= 300)
        {
            damageMap.remove(player.getUUID());
        }
    }

    @Override
    public void onUnequipped(Player player)
    {
        damageMap.remove(player.getUUID());
    }
}
