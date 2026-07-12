package cn.dawnstring.fatality.item.accessory.functional;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseShieldItem;
import cn.dawnstring.fatality.item.accessory.ShieldStats;
import cn.dawnstring.fatality.item.accessory.ShieldType;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "commanders_shield", category = ItemCategory.ACCESSORY)
public class CommandersShield extends BaseShieldItem
{
    public  CommandersShield()
    {
        super(new ShieldStats(
                0.1,
                10,
                30,
                0.06,
                0,
                ShieldType.KNOCKBACK
        ),
                List.of(
                        new StatModifier("armor", 12)
                ));
    }
}
