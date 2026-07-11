package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "life_necklace", category = ItemCategory.ACCESSORY)
public class LifeNecklace extends AccessoryItem
{
    public LifeNecklace()
    {
        super(List.of(
                new StatModifier("maxHealth", 10)
        ));
    }
}
