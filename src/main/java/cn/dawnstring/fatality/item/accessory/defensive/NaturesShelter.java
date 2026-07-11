package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "natures_shelter", category = ItemCategory.ACCESSORY)
public class NaturesShelter extends AccessoryItem implements Ability
{
    private static final Map<UUID, Integer> cooldownMap = new ConcurrentHashMap<>();

    public NaturesShelter()
    {
        super(List.of(
                new StatModifier("maxHealth", 15),
                new StatModifier("recoverHealthSpeedBonus", 3)
        ));
    }

    @Override
    public void tick(Player player)
    {
        UUID uuid = player.getUUID();
        Integer cd = cooldownMap.get(uuid);
        if (cd != null && cd > 0)
        {
            cooldownMap.put(uuid, cd - 1);
        }
        else
        {
            player.heal(1);
            cooldownMap.put(uuid, 60);
        }
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        cooldownMap.put(player.getUUID(), 60);
        return amount;
    }

    @Override
    public void onUnequipped(Player player)
    {
        cooldownMap.remove(player.getUUID());
    }
}
