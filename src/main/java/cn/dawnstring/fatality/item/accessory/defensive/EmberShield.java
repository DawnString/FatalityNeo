package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "ember_shield", category = ItemCategory.ACCESSORY)
public class EmberShield extends AccessoryItem
{
    public EmberShield()
    {
        super(List.of(
                new StatModifier("armor", 6),
                new StatModifier("maxHealth", 20)
        ));
    }
}
