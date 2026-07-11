package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;

import java.util.List;

@AutoItem(itemId = "mechanical_gauntlet", category = ItemCategory.ACCESSORY)
public class MechanicalGauntlet extends AccessoryItem
{
    public  MechanicalGauntlet()
    {
        super(List.of(
                new StatModifier("attackSpeed", 0.05f)
        ));
    }
}
