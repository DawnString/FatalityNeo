package cn.dawnstring.fatality.guide;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@AutoItem(itemId = "final_words", category = ItemCategory.MISC)
public class FinalWordsItem extends Item
{
    public FinalWordsItem()
    {
        super(new Properties().stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand)
    {
        if (level.isClientSide())
            Minecraft.getInstance().setScreen(new FinalWordsScreen());

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
