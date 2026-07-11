package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "wraith_cloak", category = ItemCategory.ACCESSORY)
public class WraithCloak extends AccessoryItem
{
    public WraithCloak()
    {
        super(List.of(
                new StatModifier("maxHealth", 10),
                new StatModifier("damageReduction", 0.05f)
        ));
    }
}
