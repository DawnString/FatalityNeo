package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "mechanical_gear", category = ItemCategory.ACCESSORY)
public class MechanicalGear extends AccessoryItem
{
    public MechanicalGear()
    {
        super(List.of(
                new StatModifier("rangedDamagePercentBonus", 0.06f),
                new StatModifier("criticalHitRate", 0.04f)
        ));
    }
}
