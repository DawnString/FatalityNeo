package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "ring_of_vengeance", category = ItemCategory.ACCESSORY)
public class RingOfVengeance extends AccessoryItem
{
    public RingOfVengeance()
    {
        super(List.of(
                new StatModifier("baseDamagePercentBonus", 0.05f),
                new StatModifier("criticalHitRate", 0.06f)
        ));
    }
}
