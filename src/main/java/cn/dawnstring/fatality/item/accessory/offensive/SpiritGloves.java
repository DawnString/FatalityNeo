package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "spirit_gloves", category = ItemCategory.ACCESSORY)
public class SpiritGloves extends AccessoryItem
{
    public SpiritGloves()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 20),
                new StatModifier("attackSpeed", 0.20f),
                new StatModifier("criticalHitRate", 0.10f)
        ));
    }
}
