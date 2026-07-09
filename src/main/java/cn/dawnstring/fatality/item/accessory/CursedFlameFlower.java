package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "cursed_flame_flower", category = ItemCategory.ACCESSORY)
public class CursedFlameFlower extends AccessoryItem
{
    public CursedFlameFlower()
    {
        super(List.of(
                new StatModifier("magicDamageValueBonus", 28),
                new StatModifier("magicCriticalDamageBonus", 0.20f),
                new StatModifier("mana", 30),
                new StatModifier("recoverManaSpeedBonus", 0.05f)
        ));
    }
}
