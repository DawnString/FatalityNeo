package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "eclipse_totem", category = ItemCategory.ACCESSORY)
public class EclipseTotem extends AccessoryItem
{
    public EclipseTotem()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 24)
        ));
    }
}
