package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "source_of_life", category = ItemCategory.ACCESSORY)
public class SourceOfLife extends AccessoryItem
{
    public SourceOfLife()
    {
        super(List.of(
                new StatModifier("maxHealth", 50),
                new StatModifier("recoverHealthSpeedBonus", 8)
        ));
    }
}
