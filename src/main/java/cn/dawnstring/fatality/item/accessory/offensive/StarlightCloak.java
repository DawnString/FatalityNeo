package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "starlight_cloak", category = ItemCategory.ACCESSORY)
public class StarlightCloak extends AccessoryItem
{
    public StarlightCloak()
    {
        super(List.of(
                new StatModifier("magicCriticalDamageBonus", 0.05f)
        ));
    }
}
