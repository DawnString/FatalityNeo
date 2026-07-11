package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "shadow_wings", category = ItemCategory.ACCESSORY)
public class ShadowWings extends BaseWingItem
{
    public  ShadowWings()
    {
        super(new WingStats(
                1.6,
                0.8,
                0.3,
                1.0,
                0.6,
                60,
                0.3
        ),
                List.of());
    }
}
