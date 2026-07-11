package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "mechanical_bracer", category = ItemCategory.ACCESSORY)
public class MechanicalBracer extends AccessoryItem
{
    public MechanicalBracer()
    {
        super(List.of(
                new StatModifier("rangedCriticalDamageBonus", 0.05f)
        ));
    }
}
