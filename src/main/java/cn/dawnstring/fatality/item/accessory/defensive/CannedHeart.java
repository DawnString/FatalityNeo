package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "canned_heart", category = ItemCategory.ACCESSORY)
public class CannedHeart extends AccessoryItem
{
    public CannedHeart()
    {
        super(List.of(
                new StatModifier("recoverHealthSpeedBonus", 0.10f)
        ));
    }
}
