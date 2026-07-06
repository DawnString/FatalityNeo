package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.utils.RandomUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@AutoItem(itemId = "watchers_eye", category = ItemCategory.ACCESSORY)
public class WatchersEye extends AccessoryItem
{
    public WatchersEye()
    {
        super(List.of(
                new StatModifier("maxHealth", 15),
                new StatModifier("armor", 2)
        ));
    }

    @Override
    public boolean onAttacked(Player player, DamageSource source, float amount)
    {
        return RandomUtil.hitProbability(5);
    }
}
