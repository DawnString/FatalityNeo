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

@AutoItem(itemId = "divine_shield", category = ItemCategory.ACCESSORY)
public class DivineShield extends BaseShieldItem
{
    public DivineShield()
    {
        super(new ShieldStats(
                0.1,
                15,
                30,
                0,
                100,
                ShieldType.PHASE
        ),
                List.of(
                        new StatModifier("armor", 15)
                ));
    }

    @Override
    public void tick(Player player)
    {
        ParticleUtil.spawnSphereParticles(player.level(), ParticleTypes.END_ROD, player, 1, 12, 0.1);
    }
}
