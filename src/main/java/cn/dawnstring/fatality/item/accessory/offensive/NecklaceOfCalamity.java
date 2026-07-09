package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "necklace_of_calamity", category = ItemCategory.ACCESSORY)
public class NecklaceOfCalamity extends AccessoryItem
{
    public NecklaceOfCalamity()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 30),
                new StatModifier("rangedCriticalDamageBonus", 0.50f),
                new StatModifier("criticalHitRate", 0.10f)
        ));
    }
}
