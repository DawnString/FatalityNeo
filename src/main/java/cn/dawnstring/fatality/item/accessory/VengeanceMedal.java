package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "vengeance_medal", category = ItemCategory.ACCESSORY)
public class VengeanceMedal extends AccessoryItem
{
    public VengeanceMedal()
    {
        super(List.of(
                new StatModifier("baseDamagePercentBonus", 0.05f)
        ));
    }
}
