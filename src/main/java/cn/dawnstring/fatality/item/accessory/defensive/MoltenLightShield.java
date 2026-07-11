package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.utils.ParticleUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "molten_light_shield", category = ItemCategory.ACCESSORY)
public class MoltenLightShield extends AccessoryItem implements Ability
{
    /**
     * COOLDOWN_TICKS 冷却时
     * RADIUS 反击半径
     * KNOCKBACK_FORCE 击退力度
     * FIRE_TICK 燃烧时间
     */
    private static final int COOLDOWN_TICKS = 100;
    private static final double RADIUS = 4.0;
    private static final double KNOCKBACK_FORCE = 1.5;
    private static final int FIRE_TICKS = 100;

    private static final Map<UUID, Integer> cooldownMap = new ConcurrentHashMap<>();

    public MoltenLightShield()
    {
        super(List.of());
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

        cooldownMap.put(player.getUUID(), COOLDOWN_TICKS);

        var box = player.getBoundingBox().inflate(RADIUS);
        for (var entity : player.level().getEntities(player, box))
        {
            if (entity instanceof LivingEntity target
                    && !target.isAlliedTo(player)
                    && target.distanceToSqr(player) <= RADIUS * RADIUS)
            {
                double dx = target.getX() - player.getX();
                double dz = target.getZ() - player.getZ();
                double dist = Math.sqrt(dx * dx + dz * dz);
                if (dist > 0.01)
                {
                    target.push(dx / dist * KNOCKBACK_FORCE, 0.3, dz / dist * KNOCKBACK_FORCE);
                }

                ParticleUtil.spawnRingParticles(player.level(), ParticleTypes.LAVA, player, 4, 32, 0.1);

                target.setRemainingFireTicks(FIRE_TICKS);
            }
        }

        return amount;
    }

    @Override
    public void onUnequipped(Player player)
    {
        cooldownMap.remove(player.getUUID());
    }

}
