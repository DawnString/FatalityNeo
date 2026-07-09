package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "rusted_talisman", category = ItemCategory.ACCESSORY)
public class RustedTalisman extends AccessoryItem
{
    public RustedTalisman()
    {
        super(List.of(
                new StatModifier("maxHealth", 10),
                new StatModifier("meleeDamagePercentBonus", 0.06f)
        ));
    }
}
