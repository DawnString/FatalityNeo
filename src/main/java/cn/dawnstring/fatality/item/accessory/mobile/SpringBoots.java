package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@AutoItem(itemId = "spring_boots", category = ItemCategory.ACCESSORY)
public class SpringBoots extends AccessoryItem
{
    public SpringBoots()
    {
        super(List.of(
                new StatModifier("moveSpeedBonus", 0.06f)
        ));
    }

    @Override
    public void tick(Player player)
    {
        if (!player.hasEffect(MobEffects.JUMP))
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 40, 1, false, false, true));
    }
}
