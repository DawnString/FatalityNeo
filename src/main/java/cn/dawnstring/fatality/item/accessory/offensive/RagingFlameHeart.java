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

@AutoItem(itemId = "raging_flame_heart", category = ItemCategory.ACCESSORY)
public class RagingFlameHeart extends AccessoryItem implements Ability
{
    public RagingFlameHeart()
    {
        super(List.of(
                new StatModifier("meleeDamagePercentBonus", 0.10f),
                new StatModifier("attackSpeed", 0.06f)
        ));

        setUniqueDes(Component.translatable("item.fatality.raging_flame_heart.unique").toString());
    }

    @Override
    public float modifyOutgoingDamage(Player player, LivingEntity target, float amount)
    {
        if (player.getHealth() / player.getMaxHealth() < 0.5f)
            return amount * 1.15f;
        return amount;
    }
}
