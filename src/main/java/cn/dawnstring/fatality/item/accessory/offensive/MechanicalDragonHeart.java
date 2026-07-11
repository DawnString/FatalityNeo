package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "mechanical_dragon_heart", category = ItemCategory.ACCESSORY)
public class MechanicalDragonHeart extends AccessoryItem
{
    public MechanicalDragonHeart()
    {
        super(List.of(
                new StatModifier("rangedDamagePercentBonus", 0.06f)
        ));
    }
}
