package cn.dawnstring.fatality.item.accessory.mobile;

import cn.dawnstring.fatality.core.input.PlayerInputState;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@AutoItem(itemId = "water_bloom_boots", category = ItemCategory.ACCESSORY)
public class WaterBloomBoots extends AccessoryItem
{
    public WaterBloomBoots()
    {
        super(List.of());
    }

    @Override
    public void tick(Player player)
    {
        if (player.isShiftKeyDown()) return;

        if (player.level().getFluidState(player.blockPosition()).is(FluidTags.WATER))
        {
            Vec3 motion = player.getDeltaMovement();
            if (PlayerInputState.isJumping(player))
                player.setDeltaMovement(motion.x, 0.42, motion.z);
            else
                player.setDeltaMovement(motion.x, Math.max(motion.y, 0), motion.z);
            player.fallDistance = 0;
        }
    }
}
