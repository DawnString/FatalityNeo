package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "fire_mana_charm", category = ItemCategory.ACCESSORY)
public class FireManaCharm extends AccessoryItem
{
    public FireManaCharm()
    {
        super(List.of(
                new StatModifier("magicDamageValueBonus", 15),
                new StatModifier("magicCriticalDamageBonus", 0.20f),
                new StatModifier("mana", 30)
        ));
    }
}
