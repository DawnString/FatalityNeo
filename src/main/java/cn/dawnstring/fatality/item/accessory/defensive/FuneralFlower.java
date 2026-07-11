package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "funeral_flower", category = ItemCategory.ACCESSORY)
public class FuneralFlower extends AccessoryItem
{
    public FuneralFlower()
    {
        super(List.of(
                new StatModifier("maxHealth", 60),
                new StatModifier("damageReduction", 0.10f),
                new StatModifier("recoverHealthSpeedBonus", 0.05f)
        ));
    }
}
