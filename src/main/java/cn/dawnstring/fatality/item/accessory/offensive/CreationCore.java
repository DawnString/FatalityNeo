package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "creation_core", category = ItemCategory.ACCESSORY)
public class CreationCore extends AccessoryItem
{
    public CreationCore()
    {
        super(List.of(
                new StatModifier("baseDamagePercentBonus", 0.18f),
                new StatModifier("criticalHitRate", 0.12f),
                new StatModifier("meleeCriticalDamageBonus", 0.15f),
                new StatModifier("rangedCriticalDamageBonus", 0.15f),
                new StatModifier("magicCriticalDamageBonus", 0.15f)
        ));
    }
}
