package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "wraith_wings", category = ItemCategory.ACCESSORY)
public class WraithWings extends BaseWingItem
{
    public WraithWings()
    {
        super(new WingStats(
                1.0,
                0.4,
                0.3,
                0.8,
                0.4,
                30,
                0.3
        ),
                List.of());
    }
}
