package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "illusion_wings", category = ItemCategory.ACCESSORY)
public class IllusionWings extends BaseWingItem
{
    public  IllusionWings()
    {
        super(new WingStats(
            1.3,
            1.0,
            0.3,
            1.0,
            0.8,
            55,
            0.3
        ),
                List.of());
    }
}
