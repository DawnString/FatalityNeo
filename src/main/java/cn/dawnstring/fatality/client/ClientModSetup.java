package cn.dawnstring.fatality.client;

import cn.dawnstring.fatality.client.gui.hud.HudOverlayRegistry;
import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.core.register.ClientModEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.common.NeoForge;

@OnlyIn(Dist.CLIENT)
public class ClientModSetup
{
    public static void init(IEventBus modEventBus)
    {
        modEventBus.addListener(RegisterGuiLayersEvent.class, HudOverlayRegistry::onRegisterLayers);
        modEventBus.addListener(EntityRenderersEvent.AddLayers.class, ClientModEvents::onAddLayers);
        modEventBus.addListener(EntityRenderersEvent.RegisterRenderers.class, ClientModEvents::onRegisterRenderers);
        modEventBus.addListener(FMLClientSetupEvent.class, ClientModEvents::onClientSetup);
        NeoForge.EVENT_BUS.addListener(HudOverlayRegistry::onRenderGuiLayerPre);
    }
}
