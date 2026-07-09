package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "wither_heart", category = ItemCategory.ACCESSORY)
public class WitherHeart extends AccessoryItem
{
    public WitherHeart()
    {
        super(List.of(
                new StatModifier("baseDamagePercentBonus", 0.08f),
                new StatModifier("armor", 4)
        ));
    }
}
