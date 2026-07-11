package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "scented_scarf", category = ItemCategory.ACCESSORY)
public class ScentedScarf extends AccessoryItem
{
    public ScentedScarf()
    {
        super(List.of(
                new StatModifier("damageReduction", 0.08f)
        ));
    }
}
