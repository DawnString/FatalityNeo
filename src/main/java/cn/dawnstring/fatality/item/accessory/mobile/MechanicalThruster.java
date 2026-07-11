package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseWingItem;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.item.accessory.WingStats;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "mechanical_thruster", category = ItemCategory.ACCESSORY)
public class MechanicalThruster extends BaseWingItem
{
    public  MechanicalThruster()
    {
        super(new WingStats(
                0.1,
                0.1,
                0.3,
                0.2,
                0.1,
                30,
                0.3
                ),
                List.of(
                new StatModifier("moveSpeedBonus", 0.1f)
        ));
    }
}
