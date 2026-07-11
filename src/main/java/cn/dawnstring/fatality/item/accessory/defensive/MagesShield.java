package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "mages_shield", category = ItemCategory.ACCESSORY)
public class MagesShield extends AccessoryItem
{
    public MagesShield()
    {
        super(List.of(
                new StatModifier("armor", 6),
                new StatModifier("mana", 60),
                new StatModifier("recoverManaSpeedBonus", 0.15f)
        ));
    }
}
