package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "seal_of_finality", category = ItemCategory.ACCESSORY)
public class SealOfFinality extends AccessoryItem
{
    public SealOfFinality()
    {
        super(List.of(
                new StatModifier("baseDamagePercentBonus", 0.12f),
                new StatModifier("criticalHitRate", 0.10f)
        ));
    }
}
