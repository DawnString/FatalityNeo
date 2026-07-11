package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "divine_remnant_shield", category = ItemCategory.ACCESSORY)
public class DivineRemnantShield extends AccessoryItem
{
    public DivineRemnantShield()
    {
        super(List.of(
                new StatModifier("damageReduction", 0.10f),
                new StatModifier("maxHealth", 20)
        ));
    }
}
