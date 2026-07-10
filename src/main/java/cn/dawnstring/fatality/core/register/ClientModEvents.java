package cn.dawnstring.fatality.core.register;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.client.effect.PlayerEffectLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ClientModEvents
{
    public static void onAddLayers(EntityRenderersEvent.AddLayers event)
    {
        PlayerRenderer defaultRenderer = event.getSkin(PlayerSkin.Model.WIDE);
        if (defaultRenderer != null)
            defaultRenderer.addLayer(new PlayerEffectLayer(defaultRenderer));

        PlayerRenderer slimRenderer = event.getSkin(PlayerSkin.Model.SLIM);
        if (slimRenderer != null)
            slimRenderer.addLayer(new PlayerEffectLayer(slimRenderer));
    }
}
