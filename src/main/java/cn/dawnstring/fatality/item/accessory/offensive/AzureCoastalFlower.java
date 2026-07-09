package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "azure_coastal_flower", category = ItemCategory.ACCESSORY)
public class AzureCoastalFlower extends AccessoryItem
{
    public AzureCoastalFlower()
    {
        super(List.of(
                new StatModifier("magicDamageValueBonus", 20),
                new StatModifier("magicCriticalDamageBonus", 0.25f),
                new StatModifier("recoverManaSpeedBonus", 0.05f)
        ));
    }
}
