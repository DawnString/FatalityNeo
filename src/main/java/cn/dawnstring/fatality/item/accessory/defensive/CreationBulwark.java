package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "creation_bulwark", category = ItemCategory.ACCESSORY)
public class CreationBulwark extends AccessoryItem
{
    public CreationBulwark()
    {
        super(List.of(
                new StatModifier("armor", 25),
                new StatModifier("damageReduction", 0.20f),
                new StatModifier("maxHealth", 100)
        ));
    }
}
