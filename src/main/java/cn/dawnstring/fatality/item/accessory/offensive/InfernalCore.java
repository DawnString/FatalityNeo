package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "infernal_core", category = ItemCategory.ACCESSORY)
public class InfernalCore extends AccessoryItem
{
    public InfernalCore()
    {
        super(List.of(
                new StatModifier("magicDamagePercentBonus", 0.08f),
                new StatModifier("mana", 30)
        ));
    }
}
