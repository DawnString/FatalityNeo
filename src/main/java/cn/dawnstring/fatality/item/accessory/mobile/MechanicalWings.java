package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "mechanical_wings", category = ItemCategory.ACCESSORY)
public class MechanicalWings extends BaseWingItem
{
    public  MechanicalWings()
    {
        super(new WingStats(
                0.8,
                0.45,
                0.3,
                0.6,
                0.4,
                35,
                0.3
        ),
                List.of());
    }
}
