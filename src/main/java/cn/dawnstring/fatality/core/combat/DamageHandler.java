package cn.dawnstring.fatality.core.combat;

import cn.dawnstring.fatality.core.capability.PlayerAttributes;
import cn.dawnstring.fatality.core.capability.PlayerAttributesProvider;
import cn.dawnstring.fatality.item.weapon.WeaponItem;
import cn.dawnstring.fatality.item.weapon.WeaponStats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.Random;

/**
 * 玩家伤害计算公式
 * 非暴击：
 * (基础伤害 + 数值伤害加成) * (1 + 伤害百分比加成) * 0.9 * random(1 - 浮动系数, 1 + 浮动系数)
 * 暴击：
 * (基础伤害 + 数值伤害加成) * (1 + 伤害百分比加成) * (1 + 暴击伤害) * 0.85 * random(1 - 浮动系数, 1 + 浮动系数)
 */
public class DamageHandler
{
    private static final Random RANDOM = new Random();
    public static final ThreadLocal<Boolean> LAST_HIT_CRIT = new ThreadLocal<>();

    public static float apply(Player attacker, LivingEntity target, DamageSource source, float amount)
    {
        ItemStack held = attacker.getMainHandItem();
        if (!(held.getItem() instanceof WeaponItem weapon)) return -1;

        WeaponStats stats = weapon.getWeaponStats();
        PlayerAttributes attributes = PlayerAttributesProvider.getAttributes(attacker);

        // 按武器类型选择对应的饰品加成
        float valueBonus = 0;
        float percentBonus = 0;
        float critDamageBonus = 0;
        switch (stats.weaponType())
        {
            case MELEE ->
            {
                valueBonus = attributes.getMeleeDamageValueBonus();
                percentBonus = attributes.getMeleeDamagePercentBonus();
                critDamageBonus = attributes.getMeleeCriticalDamageBonus();
            }
            case RANGED ->
            {
                valueBonus = attributes.getRangedDamageValueBonus();
                percentBonus = attributes.getRangedDamagePercentBonus();
                critDamageBonus = attributes.getRangedCriticalDamageBonus();
            }
            case MAGIC ->
            {
                valueBonus = attributes.getMagicDamageValueBonus();
                percentBonus = attributes.getMagicDamagePercentBonus();
                critDamageBonus = attributes.getMagicCriticalDamageBonus();
            }
        }

        // 总暴击率 = 武器暴击率 + 饰品暴击率加成
        float totalCritRate = stats.critRate() + attributes.getCriticalHitRate();
        boolean isCrit = RANDOM.nextFloat() < totalCritRate;

        // 浮动系数随机
        float fluctuation = stats.fluctuation();
        float roll = 1f - RANDOM.nextFloat() * fluctuation * 2f;

        // 总百分比加成 = 类型百分比 + 全局百分比
        float totalPercentBonus = percentBonus + attributes.getBaseDamagePercentBonus();

        // 基础伤害计算
        float damage = (stats.baseDamage() + valueBonus)
                * (1f + totalPercentBonus / 100f);
        damage *= roll;

        if (isCrit)
        {
            // (1 + 武器暴击伤害 + 饰品暴击伤害加成) * 0.85
            damage = damage * (1f + stats.critDamage() + critDamageBonus) * 0.85f;
        }
        else
        {
            damage *= 0.9f;
        }

        // 记录数据
        DebugRecorder.stageAttack(attacker.getUUID(), target.getUUID(), new DebugRecord(
                attacker.level().getGameTime(),
                target.getName().getString(),
                stats.weaponType().name(),
                amount,
                damage,
                0,
                isCrit,
                false,
                Map.of(
                        "valueBonus", valueBonus,
                        "percentBonus", percentBonus,
                        "critDamageBonus", critDamageBonus,
                        "totalCritRate", totalCritRate,
                        "fluctuation", fluctuation,
                        "roll", roll
                )
        ));

        if (!(target instanceof Player))
        {
            DebugRecorder.stageArmor(attacker.getUUID(), target.getUUID(), damage, false);
        }

        //暴击判断
        LAST_HIT_CRIT.set(isCrit);

        return Math.max(0, damage);
    }
}
