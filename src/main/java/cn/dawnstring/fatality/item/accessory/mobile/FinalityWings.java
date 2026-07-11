package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "finality_wings", category = ItemCategory.ACCESSORY)
public class FinalityWings extends BaseWingItem
{
    public  FinalityWings()
    {
        super(new WingStats(
                2.5,
                1.5,
                0.3,
                1.5,
                1.5,
                200,
                0.1
        ),
                List.of());
    }
}
