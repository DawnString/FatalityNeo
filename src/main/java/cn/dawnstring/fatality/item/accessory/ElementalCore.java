package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "element_core", category = ItemCategory.ACCESSORY)
public class ElementalCore extends AccessoryItem
{
    public ElementalCore()
    {
        super(List.of(
                new StatModifier("magicDamagePercentBonus", 0.08f),
                new StatModifier("criticalHitRate", 0.04f)
        ));
    }
}
