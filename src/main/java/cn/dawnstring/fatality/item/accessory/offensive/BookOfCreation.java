package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "book_of_creation", category = ItemCategory.ACCESSORY)
public class BookOfCreation extends AccessoryItem
{
    public BookOfCreation()
    {
        super(List.of(
                new StatModifier("baseDamagePercentBonus", 0.08f)
        ));
    }
}
