package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "sacred_flame_totem", category = ItemCategory.ACCESSORY)
public class SacredFlameTotem extends AccessoryItem
{
    public SacredFlameTotem()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 30),
                new StatModifier("rangedCriticalDamageBonus", 0.30f)
        ));
    }
}
