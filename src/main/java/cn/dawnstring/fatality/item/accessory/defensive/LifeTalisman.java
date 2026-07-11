package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "life_talisman", category = ItemCategory.ACCESSORY)
public class LifeTalisman extends AccessoryItem
{
    public LifeTalisman()
    {
        super(List.of(
                new StatModifier("maxHealth", 20),
                new StatModifier("recoverHealthSpeedBonus", 2)
        ));
    }
}
