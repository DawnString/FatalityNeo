package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "hunters_horn", category = ItemCategory.ACCESSORY)
public class HuntersHorn extends AccessoryItem
{
    public HuntersHorn()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.06f)
        ));
    }
}
