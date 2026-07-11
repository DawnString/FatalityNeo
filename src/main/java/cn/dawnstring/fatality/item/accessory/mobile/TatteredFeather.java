package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "tattered_feather", category = ItemCategory.ACCESSORY)
public class TatteredFeather extends BaseWingItem
{
    public  TatteredFeather()
    {
        super(new WingStats(
                0.8,
                0.1,
                0.3,
                0.5,
                0.1,
                20,
                0.3
        ),
                List.of());
    }
}
