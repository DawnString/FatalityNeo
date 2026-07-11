package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "wraith_bracer", category = ItemCategory.ACCESSORY)
public class WraithBracer extends AccessoryItem
{
    public WraithBracer()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 12),
                new StatModifier("rangedCriticalDamageBonus", 0.12f)
        ));
    }
}
