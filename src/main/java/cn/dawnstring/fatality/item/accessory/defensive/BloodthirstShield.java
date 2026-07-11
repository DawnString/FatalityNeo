package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "bloodthirst_shield", category = ItemCategory.ACCESSORY)
public class BloodthirstShield extends AccessoryItem implements Ability
{
    private static final Map<UUID, Float> shieldMap = new ConcurrentHashMap<>();

    public BloodthirstShield()
    {
        super(List.of(
                new StatModifier("maxHealth", 25),
                new StatModifier("recoverHealthSpeedBonus", 5)
        ));
    }

    @Override
    public void onKill(Player player, LivingEntity target)
    {
        shieldMap.put(player.getUUID(), 30f);
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        Float shield = shieldMap.get(player.getUUID());
        if (shield != null && shield > 0)
        {
            if (amount <= shield)
            {
                shieldMap.put(player.getUUID(), shield - amount);
                return 0;
            }
            else
            {
                shieldMap.remove(player.getUUID());
                return amount - shield;
            }
        }
        return amount;
    }
}
