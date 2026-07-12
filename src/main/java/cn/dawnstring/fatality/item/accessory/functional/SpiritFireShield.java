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

@AutoItem(itemId = "spiritfire_shield", category = ItemCategory.ACCESSORY)
public class SpiritFireShield extends BaseShieldItem
{
    public  SpiritFireShield()
    {
        super(new ShieldStats(
                0.2,
                25,
                20,
                0,
                200,
                ShieldType.PHASE
        ),
                List.of(
                        new StatModifier("armor", 20),
                        new StatModifier("maxHealth", 10)
                ));
    }

    @Override
    public void tick(Player player)
    {
        super.tick(player);
        ParticleUtil.spawnSphereParticles(player.level(), ParticleTypes.FLAME, player, 1, 12, 0.1);
    }
}
