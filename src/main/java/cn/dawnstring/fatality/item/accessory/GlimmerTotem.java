package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "glimmer_totem", category = ItemCategory.ACCESSORY)
public class GlimmerTotem extends AccessoryItem
{
    public GlimmerTotem()
    {
        super(List.of(
                new StatModifier("magicDamageValueBonus", 16)
        ));
    }
}
