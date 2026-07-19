package cn.dawnstring.fatality.item.armor;

import cn.dawnstring.fatality.item.UniqueItemType;
import cn.dawnstring.fatality.utils.TooltipUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public class BaseArmorItem extends ArmorItem
{
    private final ArmorStats armorStats;

    private Component story;
    private Component uniqueDes;
    private Component defaultDes;
    private Component uniqueItemTypeDes;

    public BaseArmorItem(DeferredHolder<ArmorMaterial, ArmorMaterial> material, ArmorItem.Type type, ArmorStats stats, Properties properties)
    {
        super(material, type, properties);
        this.armorStats = stats;
    }

    public ArmorStats getArmorStats()
    {
        return armorStats;
    }

    public void setStory(Component story)
    {
        this.story = story;
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("§7").append(Component.translatable("stat.fatality.damageReduction").getString()).append(": §e+").append(String.format("%.1f", armorStats.damageReduction() * 100)).append("%\n");
        sb.append("§7").append(Component.translatable("stat.fatality.penetrationResistance").getString()).append(": §e+").append(String.format("%.1f", armorStats.penetrationResistance() * 100)).append("%\n");
        sb.append("§7").append(Component.translatable("stat.fatality.penetrationResistanceCoefficient").getString()).append(": §e+").append(String.format("%.1f", armorStats.penetrationResistanceCoefficient() * 100)).append("%");

        if (uniqueDes != null)
            sb.append(uniqueDes.getString()).append("\n");

        if (story != null)
            TooltipUtil.addDescriptiveTooltip(stack, tooltip, flag, story.getString(), sb.toString(), false);
        else
            TooltipUtil.addDescriptiveTooltip(stack, tooltip, flag, null, sb.toString(), false);

        if (defaultDes != null)
            TooltipUtil.addDefaultTooltip(tooltip, defaultDes.getString(), false);

        if (uniqueItemTypeDes != null)
            TooltipUtil.addDefaultTooltip(tooltip, uniqueItemTypeDes.getString(), true);
    }
}
