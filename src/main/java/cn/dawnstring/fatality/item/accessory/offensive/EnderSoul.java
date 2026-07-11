package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "ender_soul", category = ItemCategory.ACCESSORY)
public class EnderSoul extends AccessoryItem
{
    public EnderSoul()
    {
        super(List.of(
                new StatModifier("magicDamagePercentBonus", 0.12f),
                new StatModifier("criticalHitRate", 0.08f)
        ));
    }
}
