package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.utils.ParticleUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "heart_of_mechanical_dragon", category = ItemCategory.ACCESSORY)
public class HeartOfMechanicalDragon extends AccessoryItem implements Ability
{
    private static final int COOLDOWN_TICKS = 2400;
    private static final Map<UUID, Integer> cooldownMap = new ConcurrentHashMap<>();

    public HeartOfMechanicalDragon()
    {
        super(List.of(
                new StatModifier("armor", 15)
        ));
    }

    @Override
    public void tick(Player player)
    {
        if (player.level().isClientSide())
            return;

        Integer cd = cooldownMap.get(player.getUUID());
        if (cd != null && cd > 0)
            cooldownMap.put(player.getUUID(), cd - 1);
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        Integer cd = cooldownMap.get(player.getUUID());
        if (cd != null && cd > 0)
            return amount;

        if (amount >= player.getHealth() && player.getHealth() > 0)
        {
            player.setHealth(1);
            ParticleUtil.spawnSphereParticles(player.level(), ParticleTypes.TOTEM_OF_UNDYING, player, 0.5, 32, 0.1);
            cooldownMap.put(player.getUUID(), COOLDOWN_TICKS);
            return 0;
        }

        return amount;
    }

    @Override
    public void onUnequipped(Player player)
    {
        cooldownMap.remove(player.getUUID());
    }
}
