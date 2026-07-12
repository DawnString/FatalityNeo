package cn.dawnstring.fatality.item.misc.misc;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.misc.MiscItem;
import cn.dawnstring.fatality.register.AutoItem;

@AutoItem(itemId = "emerald_coin", category = ItemCategory.MISC)
public class EmeraldCoin extends MiscItem
{
    public EmeraldCoin()
    {
        super(new Properties()
                .stacksTo(64));
    }
}
