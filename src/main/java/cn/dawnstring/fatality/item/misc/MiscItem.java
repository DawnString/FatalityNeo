package cn.dawnstring.fatality.item.misc;

import cn.dawnstring.fatality.item.UniqueItemType;
import cn.dawnstring.fatality.utils.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class MiscItem extends Item
{
    private Component uniqueDes;
    private Component defaultDes;
    private Component uniqueItemTypeDes;

    public  MiscItem(Properties properties)
    {
        super(properties);
    }

    public void setUniqueDes(Component uniqueDes)
    {
        this.uniqueDes = uniqueDes;
    }

    public void setDefaultDes(Component defaultDes)
    {
        this.defaultDes = defaultDes;
    }

    public void setUniqueItemTypeDes(UniqueItemType uniqueItemTypeDes)
    {
        switch (uniqueItemTypeDes)
        {
            case SUPPORTER_ITEM:
                this.uniqueItemTypeDes = Component.translatable("des.fatality.supporter_item");
            case DEVELOPER_ITEM:
                this.uniqueItemTypeDes = Component.translatable("des.fatality.developer_item");
        }
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

        if  (defaultDes != null)
            TooltipHelper.addDefaultTooltip(tooltipComponents, defaultDes.getString(), false);

        if (uniqueItemTypeDes != null)
            TooltipHelper.addDefaultTooltip(tooltipComponents, uniqueItemTypeDes.getString(), true);
    }
}
