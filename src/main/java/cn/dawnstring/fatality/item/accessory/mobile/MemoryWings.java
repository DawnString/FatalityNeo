package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "memory_wings", category = ItemCategory.ACCESSORY)
public class MemoryWings extends BaseWingItem
{
    public  MemoryWings()
    {
        super(new WingStats(
                2.1,
                1.3,
                0.3,
                1.6,
                1.0,
                150,
                0.1
        ),
                List.of());
    }
}
