package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "burning_soul_tear", category = ItemCategory.ACCESSORY)
public class BurningSoulTear extends AccessoryItem
{
    public BurningSoulTear()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.08f),
                new StatModifier("meleeCriticalDamageBonus", 0.12f),
                new StatModifier("rangedCriticalDamageBonus", 0.12f),
                new StatModifier("magicCriticalDamageBonus", 0.12f)
        ));
    }
}
