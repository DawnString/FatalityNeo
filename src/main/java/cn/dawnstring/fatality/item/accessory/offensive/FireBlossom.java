package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "fire_blossom", category = ItemCategory.ACCESSORY)
public class FireBlossom extends AccessoryItem
{
    public FireBlossom()
    {
        super(List.of(
                new StatModifier("magicDamageValueBonus", 25),
                new StatModifier("magicCriticalDamageBonus", 0.35f),
                new StatModifier("recoverManaSpeedBonus", 0.08f)
        ));
    }
}
