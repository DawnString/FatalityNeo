package cn.dawnstring.fatality.item.misc.misc;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.misc.MiscItem;
import cn.dawnstring.fatality.register.AutoItem;

@AutoItem(itemId = "netherite_coin", category = ItemCategory.MISC)
public class NetheriteCoin extends MiscItem
{
    public NetheriteCoin()
    {
        super(new Properties()
                .stacksTo(64));
    }
}
