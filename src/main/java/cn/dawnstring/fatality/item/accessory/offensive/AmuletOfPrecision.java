package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "amulet_of_precision", category = ItemCategory.ACCESSORY)
public class AmuletOfPrecision extends AccessoryItem
{
    public  AmuletOfPrecision()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.04f)
        ));
    }
}
