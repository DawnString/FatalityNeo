package cn.dawnstring.fatality.item.armor;

import cn.dawnstring.fatality.utils.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public class BaseArmorItem extends ArmorItem
{
    private final ArmorStats armorStats;

    public BaseArmorItem(DeferredHolder<ArmorMaterial, ArmorMaterial> material, ArmorItem.Type type, ArmorStats stats, Properties properties)
    {
        super(material, type, properties);
        this.armorStats = stats;
    }

    public ArmorStats getArmorStats()
    {
        return armorStats;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("§7").append(Component.translatable("stat.fatality.damageReduction").getString()).append(": §e+").append(String.format("%.1f", armorStats.damageReduction() * 100)).append("%\n");
        sb.append("§7").append(Component.translatable("stat.fatality.penetrationResistance").getString()).append(": §e+").append(String.format("%.1f", armorStats.penetrationResistance() * 100)).append("%\n");
        sb.append("§7").append(Component.translatable("stat.fatality.penetrationResistanceCoefficient").getString()).append(": §e+").append(String.format("%.1f", armorStats.penetrationResistanceCoefficient() * 100)).append("%");
        TooltipHelper.addDescriptiveTooltip(stack, tooltip, flag, null, sb.toString(), false);
    }
}
