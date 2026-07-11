package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "mages_shadow", category = ItemCategory.ACCESSORY)
public class MagesShadow extends AccessoryItem
{
    public MagesShadow()
    {
        super(List.of(
                new StatModifier("magicDamagePercentBonus", 0.18f),
                new StatModifier("recoverManaSpeedBonus", 0.15f)
        ));
    }
}
