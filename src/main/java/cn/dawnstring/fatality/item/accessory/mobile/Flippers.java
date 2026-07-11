package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@AutoItem(itemId = "flippers", category = ItemCategory.ACCESSORY)
public class Flippers extends AccessoryItem
{
    public Flippers()
    {
        super(List.of());
    }

    @Override
    public void tick(Player player)
    {
        if (player.isInWater() && !player.hasEffect(MobEffects.DOLPHINS_GRACE))
            player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 40, 0, false, false, true));
    }
}
