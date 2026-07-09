package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "oath_of_the_exile", category = ItemCategory.ACCESSORY)
public class OathOfTheExile extends AccessoryItem
{
    public OathOfTheExile()
    {
        super(List.of(
                new StatModifier("baseDamagePercentBonus", 0.06f),
                new StatModifier("criticalHitRate", 0.03f)
        ));
    }
}
