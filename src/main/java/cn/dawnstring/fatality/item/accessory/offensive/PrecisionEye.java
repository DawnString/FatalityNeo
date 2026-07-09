package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "precision_eye", category = ItemCategory.ACCESSORY)
public class PrecisionEye extends AccessoryItem
{
    public PrecisionEye()
    {
        super(List.of(
                new StatModifier("criticalHitRate", 0.08f),
                new StatModifier("rangedCriticalDamageBonus", 0.10f)
        ));
    }
}
