package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "serpent_totem", category = ItemCategory.ACCESSORY)
public class SerpentTotem extends AccessoryItem
{
    public SerpentTotem()
    {
        super(List.of(
                new StatModifier("rangedCriticalDamageBonus", 0.10f)
        ));
    }
}
