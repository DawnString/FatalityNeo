package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "dexterous_gloves", category = ItemCategory.ACCESSORY)
public class DexterousGloves extends AccessoryItem
{
    public DexterousGloves()
    {
        super(List.of(
                new StatModifier("rangedCriticalDamageBonus", 0.05f)
        ));
    }
}
