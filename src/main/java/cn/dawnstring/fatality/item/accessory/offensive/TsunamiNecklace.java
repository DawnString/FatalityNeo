package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "tsunami_necklace", category = ItemCategory.ACCESSORY)
public class TsunamiNecklace extends AccessoryItem
{
    public TsunamiNecklace()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 20),
                new StatModifier("meleeCriticalDamageBonus", 0.30f),
                new StatModifier("criticalHitRate", 0.08f)
        ));
    }
}
