package cn.dawnstring.fatality.item.accessory.functional;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@AutoItem(itemId = "night_vision_goggles", category = ItemCategory.ACCESSORY)
public class NightVisionGoggles extends AccessoryItem
{
    public  NightVisionGoggles()
    {
        super(List.of());
    }

    @Override
    public void tick(Player player)
    {
        if (player.hasEffect(MobEffects.NIGHT_VISION))
        {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 40, 0, false, false, true));
        }
    }
}
