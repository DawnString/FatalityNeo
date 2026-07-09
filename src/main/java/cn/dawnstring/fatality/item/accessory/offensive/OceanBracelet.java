package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "ocean_bracelet", category = ItemCategory.ACCESSORY)
public class OceanBracelet extends AccessoryItem
{
    public OceanBracelet()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 20),
                new StatModifier("rangedCriticalDamageBonus", 0.25f),
                new StatModifier("criticalHitRate", 0.08f)
        ));
    }
}
