package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "exiles_shield", category = ItemCategory.ACCESSORY)
public class ExilesShield extends AccessoryItem
{
    public ExilesShield()
    {
        super(List.of(
                new StatModifier("damageReduction", 0.08f),
                new StatModifier("maxHealth", 15)
        ));
    }
}
