package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "shadow_bracer", category = ItemCategory.ACCESSORY)
public class ShadowBracer extends AccessoryItem
{
    public ShadowBracer()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 20),
                new StatModifier("meleeCriticalDamageBonus", 0.20f),
                new StatModifier("criticalHitRate", 0.04f)
        ));
    }
}
