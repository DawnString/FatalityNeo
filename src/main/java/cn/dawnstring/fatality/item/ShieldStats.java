package cn.dawnstring.fatality.item;

/**
 * 冲刺护盾基类
 * 分为反冲与穿透
 * @param dashSpeed 冲刺速度
 * @param dashDuration 冲刺持续时间tick
 * @param cooldown 冷却时间tick
 * @param knockbackStrength 反冲力度
 * @param damageOnHit 穿透伤害
 * @param type KNOCKBACK PHASE
 */
public record ShieldStats(
        double dashSpeed,
        int dashDuration,
        int cooldown,
        double knockbackStrength,
        double damageOnHit,
        ShieldType type
) {}

enum ShieldType
{
    KNOCKBACK, PHASE
}