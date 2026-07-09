package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "sacred_flame_heart", category = ItemCategory.ACCESSORY)
public class SacredFlameHeart extends AccessoryItem
{
    public SacredFlameHeart()
    {
        super(List.of(
                new StatModifier("magicDamagePercentBonus", 0.15f),
                new StatModifier("mana", 50)
        ));
    }
}
