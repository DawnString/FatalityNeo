package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "earth_spirit_stone", category = ItemCategory.ACCESSORY)
public class EarthSpiritStone extends AccessoryItem
{
    public EarthSpiritStone()
    {
        super(List.of(
                new StatModifier("recoverHealthSpeedBonus", 0.15f)
        ));
    }
}
