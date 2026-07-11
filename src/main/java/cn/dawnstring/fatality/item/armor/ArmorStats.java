package cn.dawnstring.fatality.item.armor;

/**
 * 护甲属性
 * @param damageReduction 减伤
 * @param penetrationResistance 穿透抗性
 * @param penetrationResistanceCoefficient 抗穿透系数
 */
public record ArmorStats(
        float damageReduction,
        float penetrationResistance,
        float penetrationResistanceCoefficient
)
{}
