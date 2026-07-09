package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.utils.ParticleUtil;
import cn.dawnstring.fatality.utils.RandomUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@AutoItem(itemId = "watchers_eye", category = ItemCategory.ACCESSORY)
public class WatchersEye extends AccessoryItem implements Ability
{
    private int cooldown;

    public WatchersEye()
    {
        super(List.of(
                new StatModifier("maxHealth", 15),
                new StatModifier("armor", 2)
        ));

        setUniqueDes(Component.translatable("item.fatality.watchers_eye.unique").toString());
    }

    @Override
    public void tick(Player player)
    {
        if (cooldown > 0)
            cooldown--;
    }

    @Override
    public boolean onAttacked(Player player, DamageSource source, float amount)
    {
        if (cooldown == 0)
        {
            boolean result = RandomUtil.hitProbability(5);
            if (result)
            {
                cooldown = 400;
                ParticleUtil.spawnRingParticles(player.level(), ParticleTypes.END_ROD, player, 32, 1, 0.2);
                return true;
            }
        }
        return false;
    }
}
