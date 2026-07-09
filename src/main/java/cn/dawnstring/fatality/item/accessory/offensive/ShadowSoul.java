package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "shadow_soul", category = ItemCategory.ACCESSORY)
public class ShadowSoul extends AccessoryItem
{
    public ShadowSoul()
    {
        super(List.of(
                new StatModifier("magicDamagePercentBonus", 0.08f),
                new StatModifier("criticalHitRate", 0.06f)
        ));
    }
}
