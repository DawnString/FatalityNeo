package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "emblem_of_finality", category = ItemCategory.ACCESSORY)
public class EmblemOfFinality extends AccessoryItem
{
    public EmblemOfFinality()
    {
        super(List.of(
                new StatModifier("meleeDamagePercentBonus", 0.12f),
                new StatModifier("attackSpeed", 0.10f)
        ));
    }
}
