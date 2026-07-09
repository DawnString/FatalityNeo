package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@AutoItem(itemId = "seal_of_ruin", category = ItemCategory.ACCESSORY)
public class SealOfRuin extends AccessoryItem implements Ability
{
    public SealOfRuin()
    {
        super(List.of(
                new StatModifier("baseDamagePercentBonus", 0.15f),
                new StatModifier("meleeCriticalDamageBonus", 0.20f),
                new StatModifier("rangedCriticalDamageBonus", 0.20f),
                new StatModifier("magicCriticalDamageBonus", 0.20f)
        ));

        setUniqueDes(Component.translatable("item.fatality.seal_of_ruin.unique").toString());
    }

    @Override
    public float modifyOutgoingDamage(Player player, LivingEntity target, float amount)
    {
        if (player.getHealth() / player.getMaxHealth() < 0.2f)
            return amount * 1.5f;
        return amount;
    }
}
