package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "void_wings", category = ItemCategory.ACCESSORY)
public class VoidWings extends BaseWingItem
{
    public  VoidWings()
    {
        super(new WingStats(
                2.0,
                1.2,
                0.3,
                1.5,
                1.0,
                150,
                0.1
        ),
                List.of());
    }
}
