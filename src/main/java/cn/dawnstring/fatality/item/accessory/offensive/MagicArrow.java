package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "magic_arrow", category = ItemCategory.ACCESSORY)
public class MagicArrow extends AccessoryItem
{
    public MagicArrow()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 3)
        ));
    }
}
