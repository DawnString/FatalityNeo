package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "thorn_aura", category = ItemCategory.ACCESSORY)
public class ThornAura extends AccessoryItem
{
    public ThornAura()
    {
        super(List.of(
                new StatModifier("armor", 2)
        ));
    }
}
