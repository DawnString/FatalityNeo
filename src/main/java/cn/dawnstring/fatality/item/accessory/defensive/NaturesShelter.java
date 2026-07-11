package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@AutoItem(itemId = "natures_shelter", category = ItemCategory.ACCESSORY)
public class NaturesShelter extends AccessoryItem implements Ability
{
    private int healCooldown = 0;

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
        if (healCooldown > 0)
        {
            healCooldown--;
        }
        else
        {
            player.heal(1);
            healCooldown = 60;
        }
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        healCooldown = 60;
        return amount;
    }
}
