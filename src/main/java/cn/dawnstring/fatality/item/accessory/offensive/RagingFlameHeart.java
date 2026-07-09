package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "raging_flame_heart", category = ItemCategory.ACCESSORY)
public class RagingFlameHeart extends AccessoryItem
{
    public RagingFlameHeart()
    {
        super(List.of(
                new StatModifier("meleeDamagePercentBonus", 0.10f),
                new StatModifier("attackSpeed", 0.06f)
        ));
    }
}
