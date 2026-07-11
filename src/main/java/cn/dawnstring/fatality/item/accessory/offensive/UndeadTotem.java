package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "undead_totem", category = ItemCategory.ACCESSORY)
public class UndeadTotem extends AccessoryItem
{
    public UndeadTotem()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 25),
                new StatModifier("rangedCriticalDamageBonus", 0.30f)
        ));
    }
}
