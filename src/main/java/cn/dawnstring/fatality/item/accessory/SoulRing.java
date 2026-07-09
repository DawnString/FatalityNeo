package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "soul_ring", category = ItemCategory.ACCESSORY)
public class SoulRing extends AccessoryItem
{
    public SoulRing()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.06f)
        ));
    }
}
