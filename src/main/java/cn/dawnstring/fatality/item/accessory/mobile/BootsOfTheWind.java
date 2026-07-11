package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@AutoItem(itemId = "boots_of_the_wind", category = ItemCategory.ACCESSORY)
public class BootsOfTheWind extends AccessoryItem
{
    public BootsOfTheWind()
    {
        super(List.of(
                new StatModifier("moveSpeedBonus", 0.2f)
        ));
    }

    @Override
    public void tick(Player player)
    {
        if (!player.hasEffect(MobEffects.JUMP))
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 40, 2, false, false, true));
    }
}
