package cn.dawnstring.fatality.core.accessory;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.item.AccessoryItem;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class AccessorySlot extends Slot
{
    private Player owner;
    private SimpleContainer accessory;

    public AccessorySlot(Player player, int slot, int x, int y)
    {
        super(AccessoryManager.getInventory(player), slot, x, y);
        this.owner = player;
        this.accessory = AccessoryManager.getInventory(player);
    }

    @Override
    public boolean mayPlace(ItemStack stack)
    {
        if (!(stack.getItem() instanceof AccessoryItem)) return false;

        for (int i = 0; i < this.container.getContainerSize(); i++)
        {
            if (ItemStack.isSameItemSameComponents(this.container.getItem(i), stack))
                return false;
        }
        return true;
    }

    @Override
    public void onTake(Player player, ItemStack stack)
    {
        super.onTake(player, stack);
        onChanged();
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
    }

    private void onChanged()
    {
        AccessoryManager.save(owner);
        if (owner instanceof ServerPlayer)
            AccessoryManager.refreshAttributes(owner);
    }

    @Override
    public int getMaxStackSize(ItemStack stack)
    {
        return 1;
    }

    @Override
    public void set(ItemStack stack)
    {
        super.set(stack.isEmpty() ? ItemStack.EMPTY : stack.copy());
        onChanged();
    }

    @Override
    public ItemStack remove(int amount)
    {
        ItemStack result = super.remove(amount);
        return result.isEmpty() ? ItemStack.EMPTY : result.copy();
    }


}
