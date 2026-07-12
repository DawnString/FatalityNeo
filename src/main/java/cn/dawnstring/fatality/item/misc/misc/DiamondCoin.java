package cn.dawnstring.fatality.item.misc.misc;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.misc.MiscItem;
import cn.dawnstring.fatality.register.AutoItem;

@AutoItem(itemId = "diamond_coin", category = ItemCategory.MISC)
public class DiamondCoin extends MiscItem
{
    public DiamondCoin()
    {
        super(new Properties()
                .stacksTo(64));
    }
}
