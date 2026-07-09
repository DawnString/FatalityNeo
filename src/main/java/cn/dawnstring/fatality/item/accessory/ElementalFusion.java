package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "elemental_fusion", category = ItemCategory.ACCESSORY)
public class ElementalFusion extends AccessoryItem
{
    public ElementalFusion()
    {
        super(List.of(
                new StatModifier("magicDamagePercentBonus", 0.12f),
                new StatModifier("recoverManaSpeedBonus", 0.10f)
        ));
    }
}
