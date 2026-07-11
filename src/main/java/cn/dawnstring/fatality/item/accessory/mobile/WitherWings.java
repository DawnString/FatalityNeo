package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "wither_wings", category = ItemCategory.ACCESSORY)
public class WitherWings extends BaseWingItem
{
    public  WitherWings()
    {
        super(new WingStats(
                1.6,
                0.9,
                0.3,
                1.0,
                0.8,
                100,
                0.3
        ),
                List.of());
    }
}
