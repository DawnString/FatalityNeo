package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "starlight_talisman", category = ItemCategory.ACCESSORY)
public class StarlightTalisman extends AccessoryItem
{
    public StarlightTalisman()
    {
        super(List.of(
                new StatModifier("magicDamageValueBonus", 4)
        ));
    }
}
