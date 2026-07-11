package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "spirit_of_burning_soul", category = ItemCategory.ACCESSORY)
public class SpiritOfBurningSoul extends AccessoryItem
{
    public SpiritOfBurningSoul()
    {
        super(List.of(
                new StatModifier("armor", 8),
                new StatModifier("mana", 50)
        ));
    }
}
