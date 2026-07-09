package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "nightmare_medal", category = ItemCategory.ACCESSORY)
public class NightmareMedal extends AccessoryItem
{
    public NightmareMedal()
    {
        super(List.of(
                new StatModifier("magicCriticalDamageBonus", 0.45f),
                new StatModifier("recoverManaSpeedBonus", 0.12f),
                new StatModifier("criticalHitRate", 0.15f)
        ));
    }
}
