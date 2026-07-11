package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "soul_necklace", category = ItemCategory.ACCESSORY)
public class SoulNecklace extends AccessoryItem
{
    public SoulNecklace()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 20),
                new StatModifier("meleeCriticalDamageBonus", 0.25f),
                new StatModifier("criticalHitRate", 0.10f)
        ));
    }
}
