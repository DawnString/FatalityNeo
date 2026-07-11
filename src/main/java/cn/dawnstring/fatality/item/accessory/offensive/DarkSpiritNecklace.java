package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "dark_spirit_necklace", category = ItemCategory.ACCESSORY)
public class DarkSpiritNecklace extends AccessoryItem
{
    public DarkSpiritNecklace()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 24),
                new StatModifier("meleeCriticalDamageBonus", 0.25f),
                new StatModifier("criticalHitRate", 0.04f)
        ));
    }
}
