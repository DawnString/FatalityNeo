package cn.dawnstring.fatality.mixin;

import cn.dawnstring.fatality.Fatality;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class MixinInventoryScreen
{
    @Unique
    private static final ResourceLocation ACCESSORY_INVENTORY_LOCATION =
            ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "textures/gui/accessory_inventory.png");

    @Inject(method = "renderBg", at = @At("RETURN"))
    private void renderAccessorySlotBackgrounds(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY, CallbackInfo ci)
    {
        var screen = (ContainerScreenAccessor) this;
        int x = screen.getLeftPos() - 33;
        int y = screen.getTopPos();

        guiGraphics.blit(
            ACCESSORY_INVENTORY_LOCATION,
            x, y,
            0, 0,
            32, 140,
            32, 140
        );
    }
}
