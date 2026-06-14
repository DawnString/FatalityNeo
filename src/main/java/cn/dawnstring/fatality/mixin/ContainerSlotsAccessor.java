package cn.dawnstring.fatality.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(AbstractContainerMenu.class)
public interface ContainerSlotsAccessor
{
    @Accessor("slots")
    NonNullList<Slot> getSlots();

    @Accessor("lastSlots")
    NonNullList<ItemStack> getLastSlots();

    @Accessor("remoteSlots")
    NonNullList<ItemStack> getRemoteSlots();
}
