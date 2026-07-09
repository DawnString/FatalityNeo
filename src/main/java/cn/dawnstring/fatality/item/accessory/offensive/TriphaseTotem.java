package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "triphase_totem", category = ItemCategory.ACCESSORY)
public class TriphaseTotem extends AccessoryItem
{
    public TriphaseTotem()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 10),
                new StatModifier("rangedCriticalDamageBonus", 0.12f)
        ));
    }
}
