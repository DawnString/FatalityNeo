package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "azure_medal", category = ItemCategory.ACCESSORY)
public class AzureMedal extends AccessoryItem
{
    public AzureMedal()
    {
        super(List.of(
                new StatModifier("magicCriticalDamageBonus", 0.30f),
                new StatModifier("recoverManaSpeedBonus", 0.08f)
        ));
    }
}
