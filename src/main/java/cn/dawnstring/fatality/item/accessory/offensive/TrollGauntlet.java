package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "troll_gauntlet", category = ItemCategory.ACCESSORY)
public class TrollGauntlet extends AccessoryItem
{
    public TrollGauntlet()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 2),
                new StatModifier("meleeCriticalDamageBonus", 0.05f)
        ));
    }
}
