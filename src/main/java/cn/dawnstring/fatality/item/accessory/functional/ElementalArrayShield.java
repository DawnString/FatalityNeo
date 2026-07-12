package cn.dawnstring.fatality.item.accessory.functional;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.BaseShieldItem;
import cn.dawnstring.fatality.item.accessory.ShieldStats;
import cn.dawnstring.fatality.item.accessory.ShieldType;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.utils.ParticleUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@AutoItem(itemId = "elemental_array_shield", category = ItemCategory.ACCESSORY)
public class ElementalArrayShield extends BaseShieldItem
{
    public  ElementalArrayShield()
    {
        super(new ShieldStats(
                0.15,
                20,
                20,
                0,
                150,
                ShieldType.PHASE
        ),
                List.of(
                        new StatModifier("armor", 15)
                ));
    }

    @Override
    public void tick(Player player)
    {
        ParticleUtil.spawnSphereParticles(player.level(), ParticleTypes.LAVA, player, 1, 12, 0.1);
        ParticleUtil.spawnSphereParticles(player.level(), ParticleTypes.DRIPPING_WATER, player, 1, 12, 0.1);
    }
}
