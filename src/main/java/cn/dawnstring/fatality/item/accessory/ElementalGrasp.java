package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "elemental_grasp", category = ItemCategory.ACCESSORY)
public class ElementalGrasp extends AccessoryItem
{
    public ElementalGrasp()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 40),
                new StatModifier("attackSpeed", 0.30f),
                new StatModifier("criticalHitRate", 0.20f)
        ));
    }
}
