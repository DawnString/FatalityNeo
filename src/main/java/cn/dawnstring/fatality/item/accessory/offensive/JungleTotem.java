package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "jungle_totem", category = ItemCategory.ACCESSORY)
public class JungleTotem extends AccessoryItem
{
    public JungleTotem()
    {
        super(List.of(
                new StatModifier("rangedDamageValueBonus", 30),
                new StatModifier("rangedCriticalDamageBonus", 0.35f)
        ));
    }
}
