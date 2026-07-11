package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "abyss_dragon_orb", category = ItemCategory.ACCESSORY)
public class AbyssDragonOrb extends AccessoryItem
{
    public AbyssDragonOrb()
    {
        super(List.of(
                new StatModifier("armorToughness", 0.10f)
        ));
    }
}
