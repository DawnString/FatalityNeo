package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "withering_bloom", category = ItemCategory.ACCESSORY)
public class WitheringBloom extends AccessoryItem
{
    public WitheringBloom()
    {
        super(List.of(
                new StatModifier("magicDamageValueBonus", 20),
                new StatModifier("magicCriticalDamageBonus", 0.40f),
                new StatModifier("recoverManaSpeedBonus", 1)
        ));
    }
}
