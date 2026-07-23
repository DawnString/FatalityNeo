package cn.dawnstring.fatality.core.boss;

public record BossData(
        String id,
        String nameKey,
        String loreKey,
        String truthKey,
        int normalHp,
        int expertHp,
        BossPhase phase,
        String dimension
)
{
    public String translationKey(String suffix)
    {
        return "boss.fatality." + id + "." + suffix;
    }
}
