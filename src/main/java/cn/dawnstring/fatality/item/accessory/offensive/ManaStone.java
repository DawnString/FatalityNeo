package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "mana_stone", category = ItemCategory.ACCESSORY)
public class ManaStone extends AccessoryItem
{
    public  ManaStone()
    {
        super(List.of(
                new StatModifier("magicDamagePercentBonus", 0.05f),
                new StatModifier("mana", 20)
        ));
    }
}
