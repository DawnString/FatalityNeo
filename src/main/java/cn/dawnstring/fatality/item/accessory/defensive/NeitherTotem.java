package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@AutoItem(itemId = "neither_totem", category = ItemCategory.ACCESSORY)
public class NeitherTotem extends AccessoryItem
{
    public  NeitherTotem()
    {
        super(List.of());
    }

    @Override
    public void tick(Player player)
    {
        if (player.hasEffect(MobEffects.WITHER))
        {
            player.removeEffect(MobEffects.WITHER);
        }
    }
}
