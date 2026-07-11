package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "cast_iron_bulwark", category = ItemCategory.ACCESSORY)
public class CastIronBulwark extends AccessoryItem
{
    public CastIronBulwark()
    {
        super(List.of(
                new StatModifier("armor", 7),
                new StatModifier("damageReduction", 0.06f)
        ));
    }
}
