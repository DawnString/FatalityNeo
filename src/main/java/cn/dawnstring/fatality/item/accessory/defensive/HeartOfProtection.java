package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "heart_of_protection", category = ItemCategory.ACCESSORY)
public class HeartOfProtection extends AccessoryItem
{
    public HeartOfProtection()
    {
        super(List.of(
                new StatModifier("armor", 10),
                new StatModifier("maxHealth", 10),
                new StatModifier("recoverHealthSpeedBonus", 0.08f),
                new StatModifier("criticalHitRate", 0.10f),
                new StatModifier("baseDamagePercentBonus", 0.05f)
        ));
    }
}
