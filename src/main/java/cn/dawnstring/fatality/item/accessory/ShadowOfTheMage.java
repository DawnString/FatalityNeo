package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "shadow_of_the_mage", category = ItemCategory.ACCESSORY)
public class ShadowOfTheMage extends AccessoryItem
{
    public ShadowOfTheMage()
    {
        super(List.of(
                new StatModifier("magicDamagePercentBonus", 0.06f)
        ));
    }
}
