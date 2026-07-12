package cn.dawnstring.fatality.item.accessory.functional;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "rage_totem", category = ItemCategory.ACCESSORY)
public class RageTotem extends AccessoryItem implements Ability
{
    private static final ResourceLocation MOD_ID = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "rage_totem_attack_speed");
    private static final Map<UUID, Integer> STACKS = new ConcurrentHashMap<>();
    private static final int MAX_STACKS = 3;

    public RageTotem()
    {
        super(List.of());
    }

    @Override
    public void onKill(Player player, LivingEntity target)
    {
        UUID uuid = player.getUUID();
        int stacks = Math.min(STACKS.getOrDefault(uuid, 0) + 1, MAX_STACKS);
        STACKS.put(uuid, stacks);
        applyModifier(player, stacks);
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        UUID uuid = player.getUUID();
        if (STACKS.remove(uuid) != null)
            applyModifier(player, 0);
        return amount;
    }

    @Override
    public void onUnequipped(Player player)
    {
        STACKS.remove(player.getUUID());
        applyModifier(player, 0);
    }

    private static void applyModifier(Player player, int stacks)
    {
        if (player.level().isClientSide()) return;

        var attribute = player.getAttribute(Attributes.ATTACK_SPEED);
        if (attribute == null) return;

        attribute.removeModifier(MOD_ID);
        if (stacks > 0)
            attribute.addTransientModifier(
                    new AttributeModifier(MOD_ID, stacks * 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_BASE)
            );
    }
}
