package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "necromancers_chain", category = ItemCategory.ACCESSORY)
public class NecromancersChain extends AccessoryItem
{
    public NecromancersChain()
    {
        super(List.of(
                new StatModifier("meleeDamageValueBonus", 35),
                new StatModifier("meleeCriticalDamageBonus", 0.80f),
                new StatModifier("criticalHitRate", 0.10f)
        ));
    }
}
