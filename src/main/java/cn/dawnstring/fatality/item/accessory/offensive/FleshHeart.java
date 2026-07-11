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

@AutoItem(itemId = "flesh_heart", category = ItemCategory.ACCESSORY)
public class FleshHeart extends AccessoryItem implements Ability
{
    public FleshHeart()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 16),
                new StatModifier("maxHealth", 20)
        ));

        setUniqueDes(Component.translatable("item.fatality.flesh_heart.unique"));
    }

    @Override
    public void onKill(Player player, LivingEntity target)
    {
        player.heal(2);
    }
}
