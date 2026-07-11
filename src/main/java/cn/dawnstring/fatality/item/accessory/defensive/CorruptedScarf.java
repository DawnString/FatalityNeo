package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "corrupted_scarf", category = ItemCategory.ACCESSORY)
public class CorruptedScarf extends AccessoryItem
{
    public CorruptedScarf()
    {
        super(List.of(
                new StatModifier("damageReduction", 0.07f)
        ));
    }
}
