package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "elemental_fusion", category = ItemCategory.ACCESSORY)
public class ElementalFusion extends AccessoryItem
{
    public ElementalFusion()
    {
        super(List.of(
                new StatModifier("magicDamagePercentBonus", 0.12f),
                new StatModifier("recoverManaSpeedBonus", 1)
        ));
    }
}
