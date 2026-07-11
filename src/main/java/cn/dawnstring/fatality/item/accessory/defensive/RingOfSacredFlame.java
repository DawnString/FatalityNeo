package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "ring_of_sacred_flame", category = ItemCategory.ACCESSORY)
public class RingOfSacredFlame extends AccessoryItem implements Ability
{
    private static final double RADIUS = 4.0;
    private static final float DAMAGE_PER_SECOND = 50;
    private static final int INTERVAL = 20;
    private static final int FIRE_TICKS = 100;

    private static final Map<UUID, Integer> lastTick = new ConcurrentHashMap<>();

    public RingOfSacredFlame()
    {
        super(List.of(
                new StatModifier("armor", 10),
                new StatModifier("maxHealth", 40)
        ));
    }

    @Override
    public void tick(Player player)
    {
        if (player.level().isClientSide() || player.isSpectator())
            return;

        // 防止被 AccessoryItem.tick() 和 Ability.tick() 双重调用
        if (player.tickCount == lastTick.getOrDefault(player.getUUID(), -1))
            return;
        lastTick.put(player.getUUID(), player.tickCount);

        if (player.tickCount % INTERVAL != 0)
            return;

        var box = player.getBoundingBox().inflate(RADIUS);
        for (var entity : player.level().getEntities(player, box))
        {
            if (entity instanceof LivingEntity target
                    && !target.isAlliedTo(player)
                    && target.distanceToSqr(player) <= RADIUS * RADIUS)
            {
                target.hurt(target.damageSources().indirectMagic(player, player), DAMAGE_PER_SECOND);
                target.setRemainingFireTicks(FIRE_TICKS);
            }
        }
    }
}
