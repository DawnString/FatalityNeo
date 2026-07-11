package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "ender_shield", category = ItemCategory.ACCESSORY)
public class EnderShield extends AccessoryItem
{
    public EnderShield()
    {
        super(List.of(
                new StatModifier("damageReduction", 0.08f),
                new StatModifier("moveSpeedBonus", 0.08f)
        ));
    }
}
