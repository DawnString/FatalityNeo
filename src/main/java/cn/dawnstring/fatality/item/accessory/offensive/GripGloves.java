package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "grip_gloves", category = ItemCategory.ACCESSORY)
public class GripGloves extends AccessoryItem
{
    public  GripGloves()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.03f)
        ));
    }
}
