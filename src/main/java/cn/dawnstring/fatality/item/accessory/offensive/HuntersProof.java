package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "hunters_proof", category = ItemCategory.ACCESSORY)
public class HuntersProof extends AccessoryItem
{
    public HuntersProof()
    {
        super(List.of(
                new StatModifier("rangedDamagePercentBonus", 0.15f),
                new StatModifier("criticalHitRate", 0.08f)
        ));
    }
}
