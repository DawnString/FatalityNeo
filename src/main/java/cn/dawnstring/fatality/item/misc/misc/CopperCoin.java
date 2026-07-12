package cn.dawnstring.fatality.item.misc.misc;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.misc.MiscItem;
import cn.dawnstring.fatality.register.AutoItem;

@AutoItem(itemId = "copper_coin", category = ItemCategory.MISC)
public class CopperCoin extends MiscItem
{
    public CopperCoin()
    {
        super(new Properties()
                .stacksTo(64));
    }
}
