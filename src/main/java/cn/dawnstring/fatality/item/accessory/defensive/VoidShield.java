package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "void_shield", category = ItemCategory.ACCESSORY)
public class VoidShield extends AccessoryItem
{
    public VoidShield()
    {
        super(List.of(
                new StatModifier("armor", 12),
                new StatModifier("damageReduction", 0.10f)
        ));
    }
}
