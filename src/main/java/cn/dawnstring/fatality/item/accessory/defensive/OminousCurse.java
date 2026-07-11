package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class OminousCurse extends AccessoryItem
{
    public  OminousCurse()
    {
        super(List.of());
    }

    @Override
    public void tick(Player player)
    {
        if (player.hasEffect(MobEffects.WEAKNESS))
        {
            player.removeEffect(MobEffects.WEAKNESS);
        }
        else if(player.hasEffect(MobEffects.CONFUSION))
        {
            player.removeEffect(MobEffects.CONFUSION);
        }
    }
}
