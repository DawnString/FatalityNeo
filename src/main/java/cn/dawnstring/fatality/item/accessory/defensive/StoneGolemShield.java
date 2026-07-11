package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "stone_golem_shield", category = ItemCategory.ACCESSORY)
public class StoneGolemShield extends AccessoryItem implements Ability
{
    private static final int STAND_TICKS = 40;
    private static final float DAMAGE_REDUCTION = 0.30f;
    private static final double MOVEMENT_THRESHOLD = 0.001;

    private static final Map<UUID, Vec3> lastPos = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> standTimer = new ConcurrentHashMap<>();
    private static final Map<UUID, Boolean> buffActive = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> lastTick = new ConcurrentHashMap<>();

    public StoneGolemShield()
    {
        super(List.of(
                new StatModifier("armor", 6),
                new StatModifier("maxHealth", 15)
        ));
    }

    @Override
    public void tick(Player player)
    {
        if (player.level().isClientSide())
            return;

        if (player.tickCount == lastTick.getOrDefault(player.getUUID(), -1))
            return;
        lastTick.put(player.getUUID(), player.tickCount);

        Vec3 pos = player.position();
        Vec3 prev = lastPos.get(player.getUUID());

        if (prev != null && pos.distanceToSqr(prev) < MOVEMENT_THRESHOLD)
        {
            int timer = standTimer.getOrDefault(player.getUUID(), 0) + 1;
            if (timer >= STAND_TICKS)
                buffActive.put(player.getUUID(), true);
            standTimer.put(player.getUUID(), timer);
        }
        else
        {
            standTimer.put(player.getUUID(), 0);
            buffActive.put(player.getUUID(), false);
        }

        lastPos.put(player.getUUID(), pos);
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        if (Boolean.TRUE.equals(buffActive.get(player.getUUID())))
            return amount * (1.0f - DAMAGE_REDUCTION);

        return amount;
    }

    @Override
    public void onUnequipped(Player player)
    {
        UUID uuid = player.getUUID();
        lastPos.remove(uuid);
        standTimer.remove(uuid);
        buffActive.remove(uuid);
    }
}
