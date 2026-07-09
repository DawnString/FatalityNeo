package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "aiming_lens", category = ItemCategory.ACCESSORY)
public class AimingLens extends AccessoryItem
{
    public  AimingLens()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.05f),
                new StatModifier("rangedCriticalDamageBonus", 0.04f)
        ));
    }
}
