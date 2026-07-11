package cn.dawnstring.fatality.client.gui.hud;

import cn.dawnstring.fatality.Fatality;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class HudOverlayRegistry
{
    //取消原版的状态条，除了氧气
    public static void onRenderGuiLayerPre(RenderGuiLayerEvent.Pre event)
    {
        ResourceLocation id = event.getName();
        if (id.equals(VanillaGuiLayers.PLAYER_HEALTH)
        ||id.equals(VanillaGuiLayers.FOOD_LEVEL)
        || id.equals(VanillaGuiLayers.ARMOR_LEVEL))
            event.setCanceled(true);
    }

    public static void onRegisterLayers(RegisterGuiLayersEvent event)
    {
        event.registerAboveAll(
                ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "custom_hud"),
                FatalityHudOverlay.INSTANCE
        );
    }
}
