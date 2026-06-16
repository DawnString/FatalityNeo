package cn.dawnstring.fatality.core.combat;

import cn.dawnstring.fatality.core.capability.PlayerAttributes;
import cn.dawnstring.fatality.core.capability.PlayerAttributesProvider;
import cn.dawnstring.fatality.item.WeaponItem;
import cn.dawnstring.fatality.item.WeaponStats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

/**
 * 玩家伤害计算公式
 * 非暴击：
 * (基础伤害 + 数值伤害加成) * (1 + 伤害百分比加成) * 0.9 * random(1 - 浮动系数)
 * 暴击：
 * (基础伤害 + 数值伤害加成) * (1 + 伤害百分比加成) * 暴击伤害 * 0.85 * random(1 - 浮动系数)
 */
public class DamageHandler
{
    private static final Random RANDOM = new Random();

    public static float apply(Player attacker, DamageSource source)
    {
        ItemStack held = attacker.getMainHandItem();
        if(!(held.getItem() instanceof WeaponItem weapon)) return -1;

        WeaponStats stats = weapon.getWeaponStats();
        PlayerAttributes attributes = PlayerAttributesProvider.getAttributes(attacker);

        float valueBonus = 0;
        float critDamageBonus = 0;
        switch (stats.weaponType())
        {
            case MELEE ->
            {
                valueBonus = attributes.getMeleeDamageValueBonus();
                critDamageBonus = attributes.getMeleeCriticalDamageBonus();
            }
            case RANGED ->
            {
                valueBonus = attributes.getRangedDamageValueBonus();
                critDamageBonus = attributes.getRangedCriticalDamageBonus();
            }
            case MAGIC ->
            {
                valueBonus = attributes.getMagicDamageValueBonus();
                critDamageBonus = attributes.getMagicCriticalDamageBonus();
            }
        }

        float totalCritRate = stats.critRate() + attributes.getCriticalHitRate();
        boolean isCrit = RANDOM.nextFloat() < totalCritRate;

        float fluctuation = stats.fluctuation();
        float roll = 1f - RANDOM.nextFloat() * fluctuation *2f;

        float damage = (stats.baseDamage() + valueBonus)
                * (1f + attributes.getBaseDamagePercentBonus() / 100f);
        damage *= roll;

        if(isCrit)
        {
            damage = damage * (1f + stats.critDamage() + critDamageBonus) * 0.85f;
        }
        else
        {
            damage *= 0.9f;
        }

        return Math.max(0, damage);
    }
}
