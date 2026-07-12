package cn.dawnstring.fatality.item.misc.misc;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.misc.MiscItem;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.network.chat.Component;

@AutoItem(itemId = "golden_coin", category = ItemCategory.MISC)
public class GoldenCoin extends MiscItem
{
    public GoldenCoin()
    {
        super(new Properties()
                .stacksTo(64));

        setUniqueDes(Component.translatable("item.fatality.golden_coin.unique"));
    }
}
