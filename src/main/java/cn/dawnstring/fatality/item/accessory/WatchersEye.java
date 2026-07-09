package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "watchers_eye", category = ItemCategory.ACCESSORY)
public class WatchersEye extends AccessoryItem
{
    public WatchersEye()
    {
        super(List.of(
                new StatModifier("maxHealth", 15),
                new StatModifier("armor", 2)
        ));
    }

}
