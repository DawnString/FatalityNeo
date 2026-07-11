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

@AutoItem(itemId = "chaotic_core", category = ItemCategory.ACCESSORY)
public class ChaoticCore extends AccessoryItem implements Ability
{
    public ChaoticCore()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.06f),
                new StatModifier("meleeCriticalDamageBonus", 0.08f),
                new StatModifier("rangedCriticalDamageBonus", 0.08f),
                new StatModifier("magicCriticalDamageBonus", 0.08f)
        ));

        setUniqueDes(Component.translatable("item.fatality.chaotic_core.unique"));
    }

    @Override
    public void onHit(Player player, LivingEntity target, float amount)
    {
        if (isCrit)
        {
            player.heal(5);
        }
    }
}
