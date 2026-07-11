package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "ultimate_yin_stone", category = ItemCategory.ACCESSORY)
public class UltimateYinStone extends AccessoryItem
{
    public UltimateYinStone()
    {
        super(List.of(
                new StatModifier("armor", 30),
                new StatModifier("recoverHealthSpeedBonus", 0.10f)
        ));
    }
}
