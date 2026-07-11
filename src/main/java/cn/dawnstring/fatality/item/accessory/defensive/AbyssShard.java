package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.utils.ParticleUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "abyss_shard", category = ItemCategory.ACCESSORY)
public class AbyssShard extends AccessoryItem implements Ability
{
    private static final int MAX_CHARGES = 2;
    private static final int CHARGE_INTERVAL = 1200;
    private static final Map<UUID, Integer> charges = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> chargeTimer = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> lastTick = new ConcurrentHashMap<>();

    public AbyssShard()
    {
        super(List.of());
    }

    @Override
    public void tick(Player player)
    {
        if (player.level().isClientSide())
            return;

        if (player.tickCount == lastTick.getOrDefault(player.getUUID(), -1))
            return;
        lastTick.put(player.getUUID(), player.tickCount);

        int current = charges.getOrDefault(player.getUUID(), 0);
        if (current >= MAX_CHARGES)
            return;

        int timer = chargeTimer.getOrDefault(player.getUUID(), 0) + 1;
        if (timer >= CHARGE_INTERVAL)
        {
            charges.put(player.getUUID(), current + 1);
            chargeTimer.put(player.getUUID(), 0);
        }
        else
        {
            chargeTimer.put(player.getUUID(), timer);
        }
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        int current = charges.getOrDefault(player.getUUID(), 0);
        if (current > 0)
        {
            charges.put(player.getUUID(), current - 1);
            ParticleUtil.spawnSphereParticles(player.level(), ParticleTypes.ENCHANT, player, 1, 32, 0.1);
            return 0;
        }
        return amount;
    }

    @Override
    public void onUnequipped(Player player)
    {
        UUID uuid = player.getUUID();
        charges.remove(uuid);
        chargeTimer.remove(uuid);
    }
}
