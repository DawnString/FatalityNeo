package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "medal_of_courage", category = ItemCategory.ACCESSORY)
public class MedalOfCourage extends AccessoryItem
{
    public  MedalOfCourage()
    {
        super(List.of(
                new StatModifier("meleeDamagePercentBonus", 0.04f),
                new StatModifier("criticalHitRate", 0.03f)
        ));
    }
}
