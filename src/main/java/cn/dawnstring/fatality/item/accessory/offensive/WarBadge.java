package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "war_badge", category = ItemCategory.ACCESSORY)
public class WarBadge extends AccessoryItem
{
    public WarBadge()
    {
        super(List.of(
                new StatModifier("meleeDamagePercentBonus", 0.10f),
                new StatModifier("meleeCriticalDamageBonus", 0.12f)
        ));
    }
}
