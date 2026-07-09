package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "cursed_fire_totem", category = ItemCategory.ACCESSORY)
public class CursedFireTotem extends AccessoryItem
{
    public CursedFireTotem()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 24),
                new StatModifier("rangedCriticalDamageBonus", 0.15f)
        ));
    }
}
