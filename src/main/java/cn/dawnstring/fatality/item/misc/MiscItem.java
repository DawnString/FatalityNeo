package cn.dawnstring.fatality.item.misc;

import cn.dawnstring.fatality.utils.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class MiscItem extends Item
{
    private Component uniqueDes;

    public  MiscItem(Properties properties)
    {
        super(properties);
    }

    public void setUniqueDes(Component uniqueDes)
    {
        this.uniqueDes = uniqueDes;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag)
    {
        if (uniqueDes != null)
            TooltipHelper.addDefaultTooltip(
                    tooltipComponents,
                    uniqueDes.getString(),
                    false
            );
    }
}
