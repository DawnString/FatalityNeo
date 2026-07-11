package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "demonfire_gauntlets", category = ItemCategory.ACCESSORY)
public class DemonfireGauntlets extends AccessoryItem
{
    public DemonfireGauntlets()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 30),
                new StatModifier("attackSpeed", 0.08f),
                new StatModifier("criticalHitRate", 0.08f)
        ));
    }
}
