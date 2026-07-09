package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "bloodstained_gloves", category = ItemCategory.ACCESSORY)
public class BloodstainedGloves extends AccessoryItem
{
    public BloodstainedGloves()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 8),
                new StatModifier("attackSpeed", 0.05f),
                new StatModifier("criticalHitRate", 0.05f)
        ));
    }
}
