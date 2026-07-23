package cn.dawnstring.fatality.guide;

import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.utils.TooltipUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

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
        {
            try
            {
                Class.forName("cn.dawnstring.fatality.client.gui.ClientGuideScreenOpener")
                        .getMethod("open")
                        .invoke(null);
            }
            catch (Exception ignored) {}
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        String des = Component.translatable("item.fatality.final_words.desc").getString();
        TooltipUtil.addDefaultTooltip(tooltip, des, true);
    }
}
