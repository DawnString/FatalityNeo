package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "chaotic_core", category = ItemCategory.ACCESSORY)
public class ChaoticCore extends AccessoryItem
{
    public ChaoticCore()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.06f),
                new StatModifier("meleeCriticalDamageBonus", 0.08f),
                new StatModifier("rangedCriticalDamageBonus", 0.08f),
                new StatModifier("magicCriticalDamageBonus", 0.08f)
        ));
    }
}
