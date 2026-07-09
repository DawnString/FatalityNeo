package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "sacred_spirit_totem", category = ItemCategory.ACCESSORY)
public class SacredSpiritTotem extends AccessoryItem
{
    public SacredSpiritTotem()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 35),
                new StatModifier("rangedCriticalDamageBonus", 0.40f),
                new StatModifier("criticalHitRate", 0.08f)
        ));
    }
}
