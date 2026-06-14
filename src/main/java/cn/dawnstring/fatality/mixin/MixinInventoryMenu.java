package cn.dawnstring.fatality.mixin;

import cn.dawnstring.fatality.core.accessory.AccessoryManager;
import cn.dawnstring.fatality.core.accessory.AccessorySlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryMenu.class)
public class MixinInventoryMenu
{
    @Inject(method = "<init>", at = @At("RETURN"))
    private void addAccessorySlots(Inventory inventory, boolean active, Player player, CallbackInfo ci)
    {
        var accessor = (ContainerSlotsAccessor) this;
        for (int i = 0; i < AccessoryManager.SLOT_COUNT; i++)
        {
            var slot = new AccessorySlot(player, i, -25, 8 + i * 18);
            slot.index = accessor.getSlots().size();
            accessor.getSlots().add(slot);
            accessor.getLastSlots().add(ItemStack.EMPTY);
            accessor.getRemoteSlots().add(ItemStack.EMPTY);
        }
    }
}
