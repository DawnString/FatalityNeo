package cn.dawnstring.fatality.item;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.utils.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AccessoryItem extends Item
{
    private final List<StatModifier> modifiers;

    public AccessoryItem(Properties properties, List<StatModifier> modifiers)
    {
        super(properties.stacksTo(1).durability(0).rarity(Rarity.RARE).setNoRepair());
        this.modifiers = modifiers;
    }

    public List<StatModifier> getModifiers()
    {
        return modifiers;
    }

    public void tick(Player player) {}

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        StringBuilder att = new StringBuilder();
        for (StatModifier m : modifiers)
        {
            String sign = m.value() >= 0 ? "+" : "";
            String displayName = switch (m.field())
            {
                // 近战
                case "meleeDamageValueBonus" -> "§7近战攻击力: §e";
                case "meleeDamagePercentBonus" -> "§7近战伤害百分比: §e";
                case "meleeCriticalDamageBonus" -> "§7近战暴击伤害: §e";
                // 远程
                case "rangedDamageValueBonus" -> "§7远程攻击力: §e";
                case "rangedDamagePercentBonus" -> "§7远程伤害百分比: §e";
                case "rangedCriticalDamageBonus" -> "§7远程暴击伤害: §e";
                // 魔法
                case "magicDamageValueBonus" -> "§7魔法攻击力: §e";
                case "magicDamagePercentBonus" -> "§7魔法伤害百分比: §e";
                case "magicCriticalDamageBonus" -> "§7魔法暴击伤害: §e";
                // 通用
                case "criticalHitRate" -> "§7暴击率: §e";
                case "baseDamagePercentBonus" -> "§7全局伤害加成: §e";
                case "maxHealth" -> "§7最大生命: §e";
                case "attackSpeed" -> "§7攻击速度: §e";
                case "mana" -> "§7最大魔力: §e";
                case "moveSpeedBonus" -> "§7移动速度: §e";
                case "recoverHealthSpeedBonus" -> "§7生命恢复: §e";
                case "recoverManaSpeedBonus" -> "§7魔力恢复: §e";
                // 防御
                case "armor" -> "§7护甲值: §e";
                case "armorToughness" -> "§7护甲韧性: §e";
                case "damageReduction" -> "§7伤害抗性: §e";
                case "penetrationResistance" -> "§7穿透抗性: §e";
                case "penetrationResistanceCoefficient" -> "§7抗穿透系数: §e";
                default -> "§7" + m.field() + ": §e";
            };

            // 百分比字段显示 %
            boolean isPercent = switch (m.field())
            {
                case "meleeDamagePercentBonus", "rangedDamagePercentBonus", "magicDamagePercentBonus",
                     "criticalHitRate", "baseDamagePercentBonus",
                     "damageReduction", "penetrationResistance", "penetrationResistanceCoefficient" -> true;
                default -> false;
            };

            if (isPercent)
                att.append(displayName).append(sign).append(String.format("%.1f", m.value() * 100)).append("%");
            else
                att.append(displayName).append(sign).append((int) m.value());

            att.append("\n");
        }
        TooltipHelper.addDescriptiveTooltip(stack,
                tooltip,
                flag,
                null,
                att.toString());
    }
}
