package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "gauntlet_of_strength", category = ItemCategory.ACCESSORY)
public class GauntletOfStrength extends AccessoryItem
{
    public GauntletOfStrength()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 2),
                new StatModifier("attackSpeed", 0.05f)
        ));
    }
}
