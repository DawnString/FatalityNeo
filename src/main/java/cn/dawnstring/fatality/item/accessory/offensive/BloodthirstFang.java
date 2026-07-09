package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "bloodthirst_fang", category = ItemCategory.ACCESSORY)
public class BloodthirstFang extends AccessoryItem
{
    public BloodthirstFang()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.04f),
                new StatModifier("meleeCriticalDamageBonus", 0.06f),
                new StatModifier("rangedCriticalDamageBonus", 0.06f),
                new StatModifier("magicCriticalDamageBonus", 0.06f)
        ));
    }
}
