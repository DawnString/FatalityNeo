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

@AutoItem(itemId = "hidden_blade", category = ItemCategory.ACCESSORY)
public class HiddenBlade extends AccessoryItem implements Ability
{
    private static final Map<UUID, CooldownState> STATE = new ConcurrentHashMap<>();
    private static final int COOLDOWN_TICKS = 800;
    private static final float DAMAGE_BONUS = 15f;

    public HiddenBlade()
    {
        super(List.of());
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        if (player.level().isClientSide()) return amount;

        UUID uuid = player.getUUID();
        CooldownState state = STATE.get(uuid);
        if (state != null && state.buffed)
        {
            PlayerAttributesProvider.getAttributes(player).addBaseDamagePercentBonus(-DAMAGE_BONUS);
            STATE.put(uuid, new CooldownState(false, COOLDOWN_TICKS));
        }
        return amount;
    }

    @Override
    public void tick(Player player)
    {
        if (player.level().isClientSide()) return;

        UUID uuid = player.getUUID();
        CooldownState state = STATE.get(uuid);

        if (state == null)
        {
            PlayerAttributesProvider.getAttributes(player).addBaseDamagePercentBonus(DAMAGE_BONUS);
            STATE.put(uuid, new CooldownState(true, 0));
            return;
        }

        if (!state.buffed)
        {
            int remaining = state.remainingTicks - 1;
            if (remaining <= 0)
            {
                PlayerAttributesProvider.getAttributes(player).addBaseDamagePercentBonus(DAMAGE_BONUS);
                STATE.put(uuid, new CooldownState(true, 0));
            }
            else
            {
                STATE.put(uuid, new CooldownState(false, remaining));
            }
        }
    }

    @Override
    public void onUnequipped(Player player)
    {
        UUID uuid = player.getUUID();
        CooldownState state = STATE.remove(uuid);
        if (state != null && state.buffed && !player.level().isClientSide())
            PlayerAttributesProvider.getAttributes(player).addBaseDamagePercentBonus(-DAMAGE_BONUS);
    }

    @Override
    public void onRemove(Player player)
    {
        STATE.remove(player.getUUID());
    }

    private record CooldownState(boolean buffed, int remainingTicks) {}
}
