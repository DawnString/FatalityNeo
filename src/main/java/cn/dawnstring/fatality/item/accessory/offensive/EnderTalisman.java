package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "ender_talisman", category = ItemCategory.ACCESSORY)
public class EnderTalisman extends AccessoryItem
{
    public EnderTalisman()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.06f),
                new StatModifier("moveSpeedBonus", 0.08f)
        ));
    }
}
