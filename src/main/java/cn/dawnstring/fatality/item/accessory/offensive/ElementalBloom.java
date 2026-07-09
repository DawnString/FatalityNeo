package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "elemental_bloom", category = ItemCategory.ACCESSORY)
public class ElementalBloom extends AccessoryItem
{
    public ElementalBloom()
    {
        super(List.of(
                new StatModifier("magicDamageValueBonus", 30),
                new StatModifier("magicCriticalDamageBonus", 0.55f),
                new StatModifier("recoverManaSpeedBonus", 0.12f)
        ));
    }
}
