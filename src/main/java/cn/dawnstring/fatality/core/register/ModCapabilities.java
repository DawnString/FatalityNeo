package cn.dawnstring.fatality.core.register;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.core.capability.IPlayerAttributes;
import cn.dawnstring.fatality.core.capability.PlayerAttributesProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid =  Fatality.MODID)
public class ModCapabilities
{
    public static final EntityCapability<IPlayerAttributes, @Nullable Void> PLAYER_ATTRIBUTES = EntityCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "player_attributes"), IPlayerAttributes.class);

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event)
    {
        event.registerEntity(PLAYER_ATTRIBUTES, EntityType.PLAYER, new PlayerAttributesProvider());
    }
}
