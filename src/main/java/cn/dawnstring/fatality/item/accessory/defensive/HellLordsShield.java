package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "hell_lords_shield", category = ItemCategory.ACCESSORY)
public class HellLordsShield extends AccessoryItem
{
    public HellLordsShield()
    {
        super(List.of(
                new StatModifier("armor", 8),
                new StatModifier("maxHealth", 25)
        ));
    }
}
