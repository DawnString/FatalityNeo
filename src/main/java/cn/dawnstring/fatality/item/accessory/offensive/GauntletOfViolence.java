package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "gauntlet_of_violence", category = ItemCategory.ACCESSORY)
public class GauntletOfViolence extends AccessoryItem
{
    public GauntletOfViolence()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 3)
        ));
    }
}
