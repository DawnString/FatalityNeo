package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "mechanical_plating", category = ItemCategory.ACCESSORY)
public class MechanicalPlating extends AccessoryItem
{
    public MechanicalPlating()
    {
        super(List.of(
                new StatModifier("armor", 4),
                new StatModifier("damageReduction", 0.04f)
        ));
    }
}
