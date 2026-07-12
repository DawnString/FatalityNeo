package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.utils.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AccessoryItem extends Item
{
    private final List<StatModifier> modifiers;
    private Component uniqueDes;

    public AccessoryItem(List<StatModifier> modifiers)
    {
        super(new Properties().stacksTo(1).durability(0).rarity(Rarity.RARE).setNoRepair());
        this.modifiers = modifiers;
    }

    public List<StatModifier> getModifiers()
    {
        return modifiers;
    }

    //tick
    public void tick(Player player) {}

    //被攻击触发
    public boolean onAttacked(Player player, DamageSource source, float amount)
    {
        return false;
    }

    //从饰品栏移除时触发
    public void onUnequipped(Player player) {}

    //玩家登出/服务器关闭时清理静态状态（如有）
    public void onRemove(Player player) {}

    public void setUniqueDes(Component uniqueDes)
    {
        this.uniqueDes = uniqueDes;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        StringBuilder att = new StringBuilder();
        for (StatModifier m : modifiers)
        {
            String sign = m.value() >= 0 ? "+" : "";
            String name = Component.translatable("stat.fatality." + m.field()).getString();

            // 百分比字段显示 %
            boolean isPercent = switch (m.field())
            {
                case "meleeDamagePercentBonus", "rangedDamagePercentBonus", "magicDamagePercentBonus",
                     "meleeCriticalDamageBonus", "rangedCriticalDamageBonus", "magicCriticalDamageBonus",
                     "criticalHitRate", "baseDamagePercentBonus",
                     "damageReduction", "penetrationResistance", "penetrationResistanceCoefficient" -> true;
                default -> false;
            };

            att.append("§7").append(name).append(": §e").append(sign);
            if (isPercent)
                att.append(String.format("%.1f", m.value() * 100)).append("%");
            else
                att.append((int) m.value());
            att.append("\n");
        }
        if (uniqueDes != null)
            att.append(uniqueDes.getString()).append("\n");
        TooltipHelper.addDescriptiveTooltip(stack,
                tooltip,
                flag,
                null,
                att.toString(),
                false);
    }
}
