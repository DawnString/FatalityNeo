package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "void_eye", category = ItemCategory.ACCESSORY)
public class VoidEye extends AccessoryItem
{
    public VoidEye()
    {
        super(List.of(
                new StatModifier("magicDamagePercentBonus", 0.12f),
                new StatModifier("mana", 30)
        ));
    }
}
