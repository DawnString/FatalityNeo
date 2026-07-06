package cn.dawnstring.fatality.core.combat;

import net.minecraft.network.chat.Component;

import java.util.Map;

public record DebugRecord(
        long tick,
        String targetName,
        String weaponType,
        float originalDamage,
        float handlerOut,
        float armorOut,
        boolean isCrit,
        boolean isPenetrated,
        Map<String, Float> debugAttrs
)
{
    public String format()
    {
        StringBuilder sb = new StringBuilder();
        String target = Component.translatable("debug.fatality.target").getString();
        String crit = Component.translatable("debug.fatality.crit").getString();
        String nonCrit = Component.translatable("debug.fatality.non_crit").getString();
        String vanilla = Component.translatable("debug.fatality.vanilla").getString();
        String beforeArmor = Component.translatable("debug.fatality.before_armor").getString();
        String penetrated = Component.translatable("debug.fatality.penetrated").getString();
        String nonPen = Component.translatable("debug.fatality.non_penetrated").getString();
        String attrs = Component.translatable("debug.fatality.attrs").getString();

        sb.append(" §7").append(target).append(": §f").append(targetName).append(" §7| §f").append(weaponType);

        if (isCrit)
            sb.append(" §a(").append(crit).append(")");
        else
            sb.append(" §7(").append(nonCrit).append(")");

        sb.append("\n   §7").append(vanilla).append(": §f").append(formatDamage(originalDamage));
        sb.append(" §7→ §e").append(formatDamage(handlerOut));

        sb.append("\n   §7").append(beforeArmor).append(": §f").append(formatDamage(handlerOut));
        sb.append(" §7→ ");
        if (isPenetrated) sb.append("§c");
        else sb.append("§a");
        sb.append(formatDamage(armorOut));
        sb.append(isPenetrated ? " §c(" + penetrated + ")" : " §a(" + nonPen + ")");

        if (!debugAttrs.isEmpty())
        {
            sb.append("\n   §7").append(attrs).append(": ");
            debugAttrs.forEach((k, v) ->
                    sb.append("§f").append(k).append(": §e").append(fmt(v)).append("  "));
        }

        return sb.toString();
    }

    private static String formatDamage(float dmg)
    {
        return String.format("%.1f", dmg);
    }

    private static String fmt(float v)
    {
        return v == (int) v ? String.valueOf((int) v) : String.format("%.1f", v);
    }
}
