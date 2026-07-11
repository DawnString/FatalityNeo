package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "verdant_ring", category = ItemCategory.ACCESSORY)
public class VerdantRing extends AccessoryItem
{
    public VerdantRing()
    {
        super(List.of(
                new StatModifier("moveSpeedBonus", 0.08f),
                new StatModifier("maxHealth", 25)
        ));
    }
}
