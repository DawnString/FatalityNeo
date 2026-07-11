package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "fresh_sachet", category = ItemCategory.ACCESSORY)
public class FreshSachet extends AccessoryItem
{
    public FreshSachet()
    {
        super(List.of(
                new StatModifier("maxHealth", 20)
        ));
    }
}
