package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "mana_blessing", category = ItemCategory.ACCESSORY)
public class ManaBlessing extends AccessoryItem
{
    public ManaBlessing()
    {
        super(List.of(
                new StatModifier("magicCriticalDamageBonus", 0.10f)
        ));
    }
}
