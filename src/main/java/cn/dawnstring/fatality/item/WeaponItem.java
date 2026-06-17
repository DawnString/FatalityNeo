package cn.dawnstring.fatality.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public abstract class WeaponItem extends Item
{
    private final WeaponStats stats;

    public WeaponItem(Properties properties, WeaponStats stats)
    {
        super(properties);
        this.stats = stats;
    }

    public WeaponStats getWeaponStats()
    {
        return stats;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        String typeName = switch (stats.weaponType())
        {
            case MELEE -> "近战";
            case RANGED -> "远程";
            case MAGIC -> "魔法";
        };

        tooltip.add(Component.literal("§7类型: §e" + typeName));
        tooltip.add(Component.literal("§7面板伤害: §e" + String.format("%.1f", stats.baseDamage())));
        tooltip.add(Component.literal("§7暴击率: §e" + String.format("%.1f", stats.critRate() * 100) + "%"));
        tooltip.add(Component.literal("§7暴击伤害: §e+" + String.format("%.0f", stats.critDamage() * 100) + "%"));
        tooltip.add(Component.literal("§7浮动系数: §e" + String.format("%.2f", stats.fluctuation())));
        tooltip.add(Component.literal("§7攻击速度: §e" + String.format("%.1f", stats.attSpeed())));
    }
}
