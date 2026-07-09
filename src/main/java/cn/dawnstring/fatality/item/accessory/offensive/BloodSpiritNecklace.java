package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "blood_spirit_necklace", category = ItemCategory.ACCESSORY)
public class BloodSpiritNecklace extends AccessoryItem
{
    public BloodSpiritNecklace()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 16),
                new StatModifier("meleeCriticalDamageBonus", 0.18f)
        ));
    }
}
