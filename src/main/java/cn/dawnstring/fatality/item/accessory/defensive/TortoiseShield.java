package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "tortoise_shield", category = ItemCategory.ACCESSORY)
public class TortoiseShield extends AccessoryItem
{
    public TortoiseShield()
    {
        super(List.of(
                new StatModifier("armor", 10)
        ));
    }
}
