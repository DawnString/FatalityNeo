package cn.dawnstring.fatality.item.accessory.offensive;


import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "ring_of_power", category = ItemCategory.ACCESSORY)
public class RingOfPower extends AccessoryItem
{
    public  RingOfPower()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 3)
        ));
    }
}
