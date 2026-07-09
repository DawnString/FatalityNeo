package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "rift_crystal", category = ItemCategory.ACCESSORY)
public class RiftCrystal extends AccessoryItem
{
    public  RiftCrystal()
    {
        super(List.of(
                new StatModifier("mana", 25),
                new StatModifier("magicDamagePercentBonus", 0.08f)
        ));
    }
}
