package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "commanders_shield", category = ItemCategory.ACCESSORY)
public class CommandersShield extends AccessoryItem
{
    public CommandersShield()
    {
        super(List.of(
                new StatModifier("armor", 5),
                new StatModifier("maxHealth", 15)
        ));
    }
}
