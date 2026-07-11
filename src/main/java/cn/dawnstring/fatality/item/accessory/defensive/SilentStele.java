package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "silent_stele", category = ItemCategory.ACCESSORY)
public class SilentStele extends AccessoryItem
{
    public SilentStele()
    {
        super(List.of(
                new StatModifier("maxHealth", 20),
                new StatModifier("armor", 3)
        ));
    }
}
