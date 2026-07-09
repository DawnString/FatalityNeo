package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "cast_iron_gear", category = ItemCategory.ACCESSORY)
public class CastIronGear extends AccessoryItem
{
    public CastIronGear()
    {
        super(List.of(
                new StatModifier("rangedDamagePercentBonus", 0.08f),
                new StatModifier("criticalHitRate", 0.06f)
        ));
    }
}
