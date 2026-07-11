package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "withering_armor", category = ItemCategory.ACCESSORY)
public class WitheringArmor extends AccessoryItem
{
    public WitheringArmor()
    {
        super(List.of(
                new StatModifier("armor", 10),
                new StatModifier("damageReduction", 0.05f)
        ));
    }
}
