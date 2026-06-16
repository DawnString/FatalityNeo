package cn.dawnstring.fatality.core.combat;

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
        sb.append(" §7目标: §f").append(targetName).append(" §7| §f").append(weaponType);

        if (isCrit)
            sb.append(" §a(暴击)");
        else
            sb.append(" §7(非暴击)");

        sb.append("\n   §7原版: §f").append(formatDamage(originalDamage));
        sb.append(" §7→ §e").append(formatDamage(handlerOut));

        sb.append("\n   §7护甲前: §f").append(formatDamage(handlerOut));
        sb.append(" §7→ ");
        if (isPenetrated) sb.append("§c");
        else sb.append("§a");
        sb.append(formatDamage(armorOut));
        sb.append(isPenetrated ? " §c(穿透)" : " §a(非穿透)");

        if (!debugAttrs.isEmpty())
        {
            sb.append("\n   §7属性: ");
            debugAttrs.forEach((k, v) ->
                    sb.append("§f").append(k).append(": §e").append(v).append("  "));
        }

        return sb.toString();
    }

    private static String formatDamage(float dmg)
    {
        return String.format("%.1f", dmg);
    }
}
