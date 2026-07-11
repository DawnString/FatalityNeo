package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "ring_of_regeneration", category = ItemCategory.ACCESSORY)
public class RingOfRegeneration extends AccessoryItem
{
    public RingOfRegeneration()
    {
        super(List.of(
                new StatModifier("recoverHealthSpeedBonus", 5),
                new StatModifier("recoverManaSpeedBonus", 2)
        ));
    }
}
