package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "glimmering_cloak", category = ItemCategory.ACCESSORY)
public class GlimmeringCloak extends AccessoryItem
{
    public GlimmeringCloak()
    {
        super(List.of(
                new StatModifier("magicCriticalDamageBonus", 0.08f)
        ));
    }
}
