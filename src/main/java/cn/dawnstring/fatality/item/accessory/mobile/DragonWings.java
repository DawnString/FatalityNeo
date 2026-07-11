package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "dragon_wings", category = ItemCategory.ACCESSORY)
public class DragonWings extends BaseWingItem
{
    public  DragonWings()
    {
        super(new WingStats(
                1.8,
                1.0,
                0.3,
                1.2,
                0.9,
                100,
                0.2
        ),
                List.of());
    }
}
