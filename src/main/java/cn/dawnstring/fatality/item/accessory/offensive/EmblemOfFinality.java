package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "emblem_of_finality", category = ItemCategory.ACCESSORY)
public class EmblemOfFinality extends AccessoryItem implements Ability
{
    private static final Map<UUID, Integer> hitCountMap = new ConcurrentHashMap<>();

    public EmblemOfFinality()
    {
        super(List.of(
                new StatModifier("meleeDamagePercentBonus", 0.12f),
                new StatModifier("attackSpeed", 0.10f)
        ));

        setUniqueDes(Component.translatable("item.fatality.emblem_of_finality.unique"));
    }

    @Override
    public float modifyOutgoingDamage(Player player, LivingEntity target, float amount)
    {
        UUID uuid = player.getUUID();
        int hitCount = hitCountMap.getOrDefault(uuid, 0);
        if (hitCount == 4)
        {
            hitCountMap.put(uuid, 0);
            return amount * 2;
        }
        hitCountMap.put(uuid, hitCount + 1);
        return amount;
    }

    @Override
    public void onUnequipped(Player player)
    {
        hitCountMap.remove(player.getUUID());
    }
}
