package cn.dawnstring.fatality.item.misc.misc;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.misc.MiscItem;
import cn.dawnstring.fatality.register.AutoItem;

@AutoItem(itemId = "iron_coin", category = ItemCategory.MISC)
public class IronCoin extends MiscItem
{
    public  IronCoin()
    {
        super(new Properties().stacksTo(64));
    }
}
