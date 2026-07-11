package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "witch_hat", category = ItemCategory.ACCESSORY)
public class WitchHat extends AccessoryItem
{
    public WitchHat()
    {
        super(List.of(
                new StatModifier("magicDamageValueBonus", 8)
        ));
    }
}
