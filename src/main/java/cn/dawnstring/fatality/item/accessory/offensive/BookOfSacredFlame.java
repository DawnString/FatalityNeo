package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "book_of_sacred_flame", category = ItemCategory.ACCESSORY)
public class BookOfSacredFlame extends AccessoryItem
{
    public BookOfSacredFlame()
    {
        super(List.of(
                new StatModifier("magicDamagePercentBonus", 0.06f),
                new StatModifier("recoverManaSpeedBonus", 1)
        ));
    }
}
