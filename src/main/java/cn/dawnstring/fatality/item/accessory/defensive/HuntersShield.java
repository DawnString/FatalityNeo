package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "hunters_shield", category = ItemCategory.ACCESSORY)
public class HuntersShield extends AccessoryItem
{
    public HuntersShield()
    {
        super(List.of(
                new StatModifier("armor", 10),
                new StatModifier("damageReduction", 0.10f)
        ));
    }
}
