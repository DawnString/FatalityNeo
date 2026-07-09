package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "soul_bracer", category = ItemCategory.ACCESSORY)
public class SoulBracer extends AccessoryItem
{
    public SoulBracer()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 20),
                new StatModifier("rangedCriticalDamageBonus", 0.45f),
                new StatModifier("criticalHitRate", 0.08f)
        ));
    }
}
