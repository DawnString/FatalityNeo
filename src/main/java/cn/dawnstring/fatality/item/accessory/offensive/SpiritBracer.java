package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "spirit_bracer", category = ItemCategory.ACCESSORY)
public class SpiritBracer extends AccessoryItem
{
    public SpiritBracer()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 25),
                new StatModifier("rangedCriticalDamageBonus", 0.55f),
                new StatModifier("criticalHitRate", 0.10f)
        ));
    }
}
