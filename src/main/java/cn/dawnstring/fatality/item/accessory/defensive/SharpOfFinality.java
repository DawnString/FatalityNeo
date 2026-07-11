package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "sharp_of_finality", category = ItemCategory.ACCESSORY)
public class SharpOfFinality extends AccessoryItem
{
    public SharpOfFinality()
    {
        super(List.of(
                new StatModifier("armor", 20),
                new StatModifier("damageReduction", 0.15f),
                new StatModifier("maxHealth", 80)
        ));
    }
}
