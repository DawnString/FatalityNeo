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

@AutoItem(itemId = "emblem_of_finality", category = ItemCategory.ACCESSORY)
public class EmblemOfFinality extends AccessoryItem implements Ability
{
    private int hitCount = 0;

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
        if (hitCount == 5)
        {
            hitCount = 0;
            return amount * 2;
        }
        hitCount++;
        return amount;
    }
}
