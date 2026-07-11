package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "ironhide_shield", category = ItemCategory.ACCESSORY)
public class IronhideShield extends AccessoryItem
{
    public IronhideShield()
    {
        super(List.of(
                new StatModifier("armor", 4),
                new StatModifier("damageReduction", 0.03f)
        ));
    }
}
