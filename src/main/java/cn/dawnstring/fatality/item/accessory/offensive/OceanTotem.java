package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "ocean_totem", category = ItemCategory.ACCESSORY)
public class OceanTotem extends AccessoryItem
{
    public OceanTotem()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 20),
                new StatModifier("rangedCriticalDamageBonus", 0.25f)
        ));
    }
}
