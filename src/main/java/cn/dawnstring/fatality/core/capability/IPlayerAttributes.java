package cn.dawnstring.fatality.core.capability;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.util.INBTSerializable;

public interface IPlayerAttributes extends INBTSerializable<CompoundTag>
{
    //玩家属性
    //伤害加成，百分比加成，暴击伤害加成
    float getMeleeDamageValueBonus();
    void setMeleeDamageValueBonus(float value);
    void addMeleeDamageValueBonus(float value);

    float getMeleeDamagePercentBonus();
    void setMeleeDamagePercentBonus(float value);
    void addMeleeDamagePercentBonus(float value);

    float getMeleeCriticalDamageBonus();
    void setMeleeCriticalDamageBonus(float value);
    void addMeleeCriticalDamageBonus(float value);

    float getRangedDamageValueBonus();
    void setRangedDamageValueBonus(float value);
    void addRangedDamageValueBonus(float value);

    float getRangedDamagePercentBonus();
    void setRangedDamagePercentBonus(float value);
    void addRangedDamagePercentBonus(float value);

    float getRangedCriticalDamageBonus();
    void setRangedCriticalDamageBonus(float value);
    void addRangedCriticalDamageBonus(float value);

    float getMagicDamageValueBonus();
    void setMagicDamageValueBonus(float value);
    void addMagicDamageValueBonus(float value);

    float getMagicDamagePercentBonus();
    void setMagicDamagePercentBonus(float value);
    void addMagicDamagePercentBonus(float value);

    float getMagicCriticalDamageBonus();
    void setMagicCriticalDamageBonus(float value);
    void addMagicCriticalDamageBonus(float value);

    //暴击率
    float getCriticalHitRate();
    void setCriticalHitRate(float value);
    void addCriticalHitRate(float value);

    //基础伤害加成
    float getBaseDamagePercentBonus();
    void setBaseDamagePercentBonus(float value);
    void addBaseDamagePercentBonus(float value);

    //最大血量
    int getMaxHealthBonus();
    void setMaxHealthBonus(int value);
    void addMaxHealthBonus(int value);

    //攻击速度
    float getAttackSpeed();
    void setAttackSpeed(float value);
    void addAttackSpeed(float value);

    //最大魔力
    int getMaxMana();
    void setMaxMana(int value);
    void addMaxMana(int value);

    //魔力
    int getCurrentMana();
    void setCurrentMana(int value);
    void addCurrentMana(int value);
    boolean consumeMana(int amount);

    //移动速度加成
    float getMoveSpeedBonus();
    void setMoveSpeedBonus(float value);
    void addMoveSpeedBonus(float value);

    //回血速度加成
    float getRecoverHealthSpeedBonus();
    void setRecoverHealthSpeedBonus(float value);
    void addRecoverHealthSpeedBonus(float value);

    //回法速度加成
    float getRecoverManaSpeedBonus();
    void setRecoverManaSpeedBonus(float value);
    void addRecoverManaSpeedBonus(float value);

    //护甲值
    int getArmor();
    void setArmor(int value);
    void addArmor(int value);

    //伤害减免
    float getDamageReduction();
    void setDamageReduction(float value);
    void addDamageReduction(float value);

    //穿透抗性
    float getPenetrationResistance();
    void setPenetrationResistance(float value);
    void addPenetrationResistance(float value);

    //抗穿透系数
    float getPenetrationResistanceCoefficient();
    void setPenetrationResistanceCoefficient(float value);
    void addPenetrationResistanceCoefficient(float value);

    //护甲韧性
    float getArmorToughness();
    void setArmorToughness(float value);
    void addArmorToughness(float value);

    void copyFrom(IPlayerAttributes source);

    void tick();
}
