package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "bloodfang_necklace", category = ItemCategory.ACCESSORY)
public class BloodfangNecklace extends AccessoryItem
{
    public BloodfangNecklace()
    {
        super(List.of(
                new StatModifier("meleeCriticalDamageBonus", 0.10f)
        ));
    }
}
