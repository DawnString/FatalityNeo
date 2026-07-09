package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "sacred_flame_gauntlets", category = ItemCategory.ACCESSORY)
public class SacredFlameGauntlets extends AccessoryItem
{
    public SacredFlameGauntlets()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 30),
                new StatModifier("meleeDamagePercentBonus", 0.35f),
                new StatModifier("attackSpeed", 0.25f),
                new StatModifier("criticalHitRate", 0.15f)
        ));
    }
}
