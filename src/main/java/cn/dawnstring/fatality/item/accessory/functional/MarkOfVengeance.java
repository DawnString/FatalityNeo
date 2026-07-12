package cn.dawnstring.fatality.item.accessory.functional;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.core.capability.PlayerAttributesProvider;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "mark_of_vengeance", category = ItemCategory.ACCESSORY)
public class MarkOfVengeance extends AccessoryItem implements Ability
{
    private static final Map<UUID, Integer> CRIT_STATE = new ConcurrentHashMap<>();
    private static final int STATE_NORMAL = 0;
    private static final int STATE_PENDING = 1;
    private static final int STATE_BUFFED = 2;
    private static final float CRIT_BOOST = 100f;

    public MarkOfVengeance()
    {
        super(List.of());
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        UUID uuid = player.getUUID();
        if (CRIT_STATE.getOrDefault(uuid, STATE_NORMAL) == STATE_NORMAL)
            CRIT_STATE.put(uuid, STATE_PENDING);
        return amount;
    }

    @Override
    public void tick(Player player)
    {
        if (player.level().isClientSide()) return;

        UUID uuid = player.getUUID();
        if (CRIT_STATE.getOrDefault(uuid, STATE_NORMAL) == STATE_PENDING)
        {
            PlayerAttributesProvider.getAttributes(player).addCriticalHitRate(CRIT_BOOST);
            CRIT_STATE.put(uuid, STATE_BUFFED);
        }
    }

    @Override
    public void onHit(Player player, LivingEntity target, float amount)
    {
        if (player.level().isClientSide()) return;

        UUID uuid = player.getUUID();
        if (CRIT_STATE.getOrDefault(uuid, STATE_NORMAL) == STATE_BUFFED)
        {
            PlayerAttributesProvider.getAttributes(player).addCriticalHitRate(-CRIT_BOOST);
            CRIT_STATE.remove(uuid);
        }
    }

    @Override
    public void onUnequipped(Player player)
    {
        UUID uuid = player.getUUID();
        Integer state = CRIT_STATE.remove(uuid);
        if (state != null && state == STATE_BUFFED && !player.level().isClientSide())
            PlayerAttributesProvider.getAttributes(player).addCriticalHitRate(-CRIT_BOOST);
    }

    @Override
    public void onRemove(Player player)
    {
        CRIT_STATE.remove(player.getUUID());
    }
}
