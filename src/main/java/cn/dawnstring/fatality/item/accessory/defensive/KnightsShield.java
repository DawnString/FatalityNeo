package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "knights_shield", category = ItemCategory.ACCESSORY)
public class KnightsShield extends AccessoryItem
{
    public KnightsShield()
    {
        super(List.of(
                new StatModifier("armor", 15)
        ));
    }
}
