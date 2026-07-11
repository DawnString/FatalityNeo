package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "infernal_wings", category = ItemCategory.ACCESSORY)
public class InfernalWings extends BaseWingItem
{
    public  InfernalWings()
    {
        super(new WingStats(
                1.7,
                0.9,
                0.3,
                1.1,
                0.8,
                70,
                0.3
        ),
                List.of());
    }
}
