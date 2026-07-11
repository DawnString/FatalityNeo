package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "wind_boots", category = ItemCategory.ACCESSORY)
public class WindBoots extends AccessoryItem
{
    public  WindBoots()
    {
        super(List.of(
                new StatModifier("moveSpeedBonus", 0.1f)
        ));
    }
}
