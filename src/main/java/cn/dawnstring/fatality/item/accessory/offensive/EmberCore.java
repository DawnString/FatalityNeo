package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "ember_core", category = ItemCategory.ACCESSORY)
public class EmberCore extends AccessoryItem
{
    public EmberCore()
    {
        super(List.of(
                new StatModifier("meleeDamagePercentBonus", 0.10f),
                new StatModifier("criticalHitRate", 0.06f)
        ));
    }
}
