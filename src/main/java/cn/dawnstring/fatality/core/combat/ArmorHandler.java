package cn.dawnstring.fatality.core.combat;

import cn.dawnstring.fatality.core.capability.PlayerAttributes;
import cn.dawnstring.fatality.core.capability.PlayerAttributesProvider;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

/**
 * 护甲伤害计算
 * 穿透计算：穿透概率 = (受到伤害 - 护甲值) / 受到伤害 * (1 - 抗穿透系数) + 随机数(+-0.1)
 * 非穿透: 实际伤害 = 受到伤害 * (1 - 护甲值 / (护甲值 + 平衡系数 * 抗穿透系数)) * (1 - 伤害抗性)
 * 穿透：实际伤害 = 受到伤害 * (1 - 护甲值 * 护甲韧性 / (护甲值 * 护甲韧性 + 平衡系数)) * (1 - 穿透抗性) * (1 + 穿透随机加成)
 */
public class ArmorHandler
{
    private static final Random RANDOM = new Random();
    private static final int BALANCE = 5;

    public static float apply(float rawDamage, Player target, DamageSource source)
    {
        PlayerAttributes attributes = PlayerAttributesProvider.getAttributes(target);
        float armor = attributes.getArmor();
        float toughness = attributes.getArmorToughness();
        //穿透抗性
        float penResist = attributes.getPenetrationResistance();
        //抗穿透系数
        float penResistCoefficient = attributes.getPenetrationResistanceCoefficient();
        //减伤
        float damageReduction = attributes.getDamageReduction();

        //基础值
        float baseProb = (rawDamage - armor) / Math.max(1, rawDamage) * (1 - penResistCoefficient);
        //随机数
        float randomOffset = RANDOM.nextFloat() * 0.2f - 0.1f;

        float penProb = Math.max(0, Math.min(1, baseProb + randomOffset));

        //穿透概率
        boolean penetrated = RANDOM.nextFloat() < penProb;

        //穿透随机加成，按照难度选取
        float penBonus = getPenetrationRandomBonus(target.level().getDifficulty());

        float finalDamage;

        if(penetrated)
        {
            finalDamage = rawDamage * (1 - armor * toughness / (armor * toughness + BALANCE)) * (1 - penResist) * (1 + penBonus);
        }
        else
        {
            finalDamage = rawDamage * (1 - armor / (armor + BALANCE * penResist)) * (1 - damageReduction);
        }
        return Math.max(0, finalDamage);
    }

    private static float getPenetrationRandomBonus(Difficulty difficulty)
    {
        return switch (difficulty)
        {
            case EASY -> RANDOM.nextFloat() * 0.1f;
            case NORMAL -> 0.1f + RANDOM.nextFloat() * 0.05f;
            case HARD -> 0.15f + RANDOM.nextFloat() * 0.1f;
            default -> 0;
        };
    }
}
