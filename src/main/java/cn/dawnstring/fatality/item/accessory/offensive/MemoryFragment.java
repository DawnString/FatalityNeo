package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import java.util.List;

@AutoItem(itemId = "memory_fragment", category = ItemCategory.ACCESSORY)
public class MemoryFragment extends AccessoryItem
{
    public MemoryFragment()
    {
        super(List.of(
                new StatModifier("baseDamagePercentBonus", 0.10f),
                new StatModifier("criticalHitRate", 0.06f)
        ));
    }
}
