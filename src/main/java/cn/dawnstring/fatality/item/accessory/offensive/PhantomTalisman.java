package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "phantom_talisman", category = ItemCategory.ACCESSORY)
public class PhantomTalisman extends AccessoryItem
{
    public PhantomTalisman()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.06f)
        ));
    }
}
