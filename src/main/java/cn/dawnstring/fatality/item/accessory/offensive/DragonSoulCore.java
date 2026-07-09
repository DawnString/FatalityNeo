package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "dragon_soul_core", category = ItemCategory.ACCESSORY)
public class DragonSoulCore extends AccessoryItem
{
    public DragonSoulCore()
    {
        super(List.of(
                new StatModifier("rangedDamagePercentBonus", 0.12f),
                new StatModifier("criticalHitRate", 0.06f)
        ));
    }
}
