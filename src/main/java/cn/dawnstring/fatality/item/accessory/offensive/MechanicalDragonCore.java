package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "mechanical_dragon_core", category = ItemCategory.ACCESSORY)
public class MechanicalDragonCore extends AccessoryItem
{
    public MechanicalDragonCore()
    {
        super(List.of(
                new StatModifier("rangedDamagePercentBonus", 0.12f),
                new StatModifier("criticalHitRate", 0.10f)
        ));
    }
}
