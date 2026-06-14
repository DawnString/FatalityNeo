package cn.dawnstring.fatality.core.capability;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerAttributesProvider implements ICapabilityProvider<Entity, Void, IPlayerAttributes>
{
    private static final String ATTRIBUTES_KEY = "FatalityPlayerAttributes";

    private static final Map<UUID, PlayerAttributes> ATTRIBUTES = new HashMap<>();

    public static PlayerAttributes getAttributes(Player player)
    {
        return ATTRIBUTES.computeIfAbsent(player.getUUID(), id -> {
            PlayerAttributes attrs = new PlayerAttributes();
            CompoundTag tag = (CompoundTag) player.getPersistentData().get(ATTRIBUTES_KEY);
            if (tag != null)
            {
                attrs.deserializeNBT(RegistryAccess.EMPTY, tag);
            }
            return attrs;
        });
    }

    public static void updateAttributes(Player player, PlayerAttributes attrs)
    {
        ATTRIBUTES.put(player.getUUID(), attrs);
    }

    @Override
    public @Nullable IPlayerAttributes getCapability(Entity entity, Void context)
    {
        if (entity instanceof Player player)
        {
            return getAttributes(player);
        }
        return null;
    }

    public static void remove(UUID uuid)
    {
        ATTRIBUTES.remove(uuid);
    }
}
