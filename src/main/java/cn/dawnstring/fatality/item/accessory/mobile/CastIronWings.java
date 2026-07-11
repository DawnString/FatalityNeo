package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "cast_iron_wings", category = ItemCategory.ACCESSORY)
public class CastIronWings extends BaseWingItem
{
    public  CastIronWings()
    {
        super(new WingStats(
                1.2,
                0.6,
                0.3,
                0.8,
                0.4,
                50,
                0.3
        ),
                List.of());
    }
}
