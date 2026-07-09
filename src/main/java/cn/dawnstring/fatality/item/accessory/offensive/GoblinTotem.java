package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "goblin_totem", category = ItemCategory.ACCESSORY)
public class GoblinTotem extends AccessoryItem
{
    public GoblinTotem()
    {
        super(List.of(
                new StatModifier("rangedCriticalDamageBonus", 0.06f)
        ));
    }
}
