package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "spiritfire_wings", category = ItemCategory.ACCESSORY)
public class SpiritFireWings extends BaseWingItem
{
    public  SpiritFireWings()
    {
        super(new WingStats(
                1.8,
                0.9,
                0.3,
                1.2,
                0.8,
                120,
                0.2
        ),
                List.of());
    }
}
