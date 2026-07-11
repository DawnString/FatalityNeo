package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.utils.ParticleUtil;
import cn.dawnstring.fatality.utils.RandomUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@AutoItem(itemId = "creation_core", category = ItemCategory.ACCESSORY)
public class CreationCore extends AccessoryItem implements Ability
{
    public CreationCore()
    {
        super(List.of(
                new StatModifier("baseDamagePercentBonus", 0.18f),
                new StatModifier("criticalHitRate", 0.12f),
                new StatModifier("meleeCriticalDamageBonus", 0.15f),
                new StatModifier("rangedCriticalDamageBonus", 0.15f),
                new StatModifier("magicCriticalDamageBonus", 0.15f)
        ));

        setUniqueDes(Component.translatable("item.fatality.creation_core.unique"));
    }

    @Override
    public float modifyOutgoingDamage(Player player, LivingEntity target, float amount)
    {
        if (RandomUtil.hitProbability(10))
        {
            ParticleUtil.spawnRingParticles(player.level(), ParticleTypes.END_ROD, player, 1, 1, 0.2);

            return amount * 1.5f;
        }

        return amount;
    }
}
