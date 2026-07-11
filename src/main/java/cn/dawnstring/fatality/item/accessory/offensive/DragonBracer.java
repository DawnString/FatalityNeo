package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "dragon_bracer", category = ItemCategory.ACCESSORY)
public class DragonBracer extends AccessoryItem
{
    public DragonBracer()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 8),
                new StatModifier("rangedCriticalDamageBonus", 0.15f),
                new StatModifier("criticalHitRate", 0.04f)
        ));
    }
}
