package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "archmage_medal", category = ItemCategory.ACCESSORY)
public class ArchmageMedal extends AccessoryItem
{
    public ArchmageMedal()
    {
        super(List.of(
                new StatModifier("magicCriticalDamageBonus", 0.25f),
                new StatModifier("recoverManaSpeedBonus", 0.08f)
        ));
    }
}
