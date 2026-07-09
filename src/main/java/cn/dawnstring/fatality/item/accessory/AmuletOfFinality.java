package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "amulet_of_finality", category = ItemCategory.ACCESSORY)
public class AmuletOfFinality extends AccessoryItem
{
    public AmuletOfFinality()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 40),
                new StatModifier("meleeCriticalDamageBonus", 0.90f),
                new StatModifier("criticalHitRate", 0.12f)
        ));
    }
}
