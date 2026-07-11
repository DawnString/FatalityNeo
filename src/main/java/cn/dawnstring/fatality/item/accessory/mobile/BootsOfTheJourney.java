package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "boots_of_the_journey", category = ItemCategory.ACCESSORY)
public class BootsOfTheJourney extends AccessoryItem
{
    public BootsOfTheJourney()
    {
        super(List.of(
                new StatModifier("moveSpeedBonus", 0.08f),
                new StatModifier("recoverHealthSpeedBonus", 1),
                new StatModifier("armor", 5)
        ));
    }
}
