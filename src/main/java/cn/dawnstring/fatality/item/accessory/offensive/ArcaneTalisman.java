package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "arcane_talisman", category = ItemCategory.ACCESSORY)
public class ArcaneTalisman extends AccessoryItem
{
    public ArcaneTalisman()
    {
        super(List.of(
                new StatModifier("magicDamageValueBonus", 12),
                new StatModifier("magicCriticalDamageBonus", 0.12f),
                new StatModifier("mana", 30)
        ));
    }
}
