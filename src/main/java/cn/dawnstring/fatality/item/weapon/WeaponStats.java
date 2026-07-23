package cn.dawnstring.fatality.item.weapon;

/**
 * 武器属性
 * @param baseDamage 面板伤害
 * @param critRate 暴击率
 * @param critDamage 暴击伤害
 * @param fluctuation 浮动系数
 * @param attSpeed 攻击速度
 * @param weaponType 武器类型
 * @param attackMode 攻击方式
 * @param manaCost 魔法消耗
 * @param cooldownTicks 冷却时间
 */
public record WeaponStats(
        float baseDamage,
        float critRate,
        float critDamage,
        float fluctuation,
        float attSpeed,
        WeaponType weaponType,
        AttackMode attackMode,
        int manaCost,
        int cooldownTicks
) { }
