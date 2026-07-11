package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "frostfeather_wings", category = ItemCategory.ACCESSORY)
public class FrostFeatherWings extends BaseWingItem
{
    public  FrostFeatherWings()
    {
        super(new WingStats(
                1.2,
                0.7,
                0.3,
                0.8,
                0.45,
                50,
                0.3
        ),
                List.of());
    }
}
