package cn.dawnstring.fatality.core.capability;

import cn.dawnstring.fatality.core.network.SyncAttributesPacket;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public class PlayerAttributes implements IPlayerAttributes
{
    private float meleeDamageValueBonus = 0;
    private float meleeDamagePercentBonus = 0;
    private float meleeCriticalDamageBonus = 0;
    private float rangedDamageValueBonus = 0;
    private float rangedDamagePercentBonus = 0;
    private float rangedCriticalDamageBonus = 0;
    private float magicDamageValueBonus = 0;
    private float magicDamagePercentBonus = 0;
    private float magicCriticalDamageBonus = 0;
    private float criticalHitRate = 0.05f;
    private float baseDamagePercentBonus = 0;
    private int maxHealth = 20;
    private float attackSpeed = 0;
    private int mana = 100;
    private int currentMana = mana;
    private float moveSpeedBonus = 0;
    private float recoverHealthSpeedBonus  = 0;
    private float recoverManaSpeedBonus = 0;
    private int armor = 0;
    private float damageReduction = 0;
    private float penetrationResistance = 0;
    private float penetrationResistanceCoefficient = 0;
    private float armorToughness = 0;

    // 内部状态
    private int regenCooldown = 0;

    @Override
    public float getMeleeDamageValueBonus()
    {
        return meleeDamageValueBonus;
    }
    @Override
    public void setMeleeDamageValueBonus(float value)
    {
        meleeDamageValueBonus = value;
    }
    @Override
    public void addMeleeDamageValueBonus(float value)
    {
        meleeDamageValueBonus += value;
    }
    @Override
    public float getMeleeDamagePercentBonus()
    {
        return  meleeDamagePercentBonus;
    }
    @Override
    public void setMeleeDamagePercentBonus(float value)
    {
        meleeDamagePercentBonus = value;
    }
    @Override
    public void addMeleeDamagePercentBonus(float value)
    {
        meleeDamagePercentBonus += value;
    }
    @Override
    public float getMeleeCriticalDamageBonus()
    {
        return meleeCriticalDamageBonus;
    }
    @Override
    public void setMeleeCriticalDamageBonus(float value)
    {
        meleeCriticalDamageBonus = value;
    }
    @Override
    public void addMeleeCriticalDamageBonus(float value)
    {
        meleeCriticalDamageBonus += value;
    }
    @Override
    public float getRangedDamageValueBonus()
    {
        return rangedDamageValueBonus;
    }
    @Override
    public void setRangedDamageValueBonus(float value)
    {
        rangedDamageValueBonus = value;
    }
    @Override
    public void addRangedDamageValueBonus(float value)
    {
        rangedDamageValueBonus += value;
    }
    @Override
    public float getRangedDamagePercentBonus()
    {
        return rangedDamagePercentBonus;
    }
    @Override
    public void setRangedDamagePercentBonus(float value)
    {
        rangedDamagePercentBonus = value;
    }
    @Override
    public void addRangedDamagePercentBonus(float value)
    {
        rangedDamagePercentBonus += value;
    }
    @Override
    public float getRangedCriticalDamageBonus()
    {
        return rangedCriticalDamageBonus;
    }
    @Override
    public void setRangedCriticalDamageBonus(float value)
    {
        rangedCriticalDamageBonus = value;
    }
    @Override
    public void addRangedCriticalDamageBonus(float value)
    {
        rangedCriticalDamageBonus += value;
    }
    @Override
    public float getMagicDamageValueBonus()
    {
        return magicDamageValueBonus;
    }
    @Override
    public void setMagicDamageValueBonus(float value)
    {
        magicDamageValueBonus = value;
    }
    @Override
    public void addMagicDamageValueBonus(float value)
    {
        magicDamageValueBonus += value;
    }
    @Override
    public float getMagicDamagePercentBonus()
    {
        return magicDamagePercentBonus;
    }
    @Override
    public void setMagicDamagePercentBonus(float value)
    {
        magicDamagePercentBonus = value;
    }
    @Override
    public void addMagicDamagePercentBonus(float value)
    {
        magicDamagePercentBonus += value;
    }
    @Override
    public float getMagicCriticalDamageBonus()
    {
        return magicCriticalDamageBonus;
    }
    @Override
    public void setMagicCriticalDamageBonus(float value)
    {
        magicCriticalDamageBonus = value;
    }
    @Override
    public void addMagicCriticalDamageBonus(float value)
    {
        magicCriticalDamageBonus += value;
    }
    @Override
    public float getCriticalHitRate()
    {
        return criticalHitRate;
    }
    @Override
    public void setCriticalHitRate(float value)
    {
        criticalHitRate = value;
    }
    @Override
    public void addCriticalHitRate(float value)
    {
        criticalHitRate += value;
    }
    @Override
    public float getBaseDamagePercentBonus()
    {
        return baseDamagePercentBonus;
    }
    @Override
    public void setBaseDamagePercentBonus(float value)
    {
        baseDamagePercentBonus = value;
    }
    @Override
    public void addBaseDamagePercentBonus(float value)
    {
        baseDamagePercentBonus += value;
    }
    @Override
    public int getMaxHealthBonus()
    {
        return maxHealth;
    }
    @Override
    public void setMaxHealthBonus(int value)
    {
        maxHealth = value;
    }
    @Override
    public void addMaxHealthBonus(int value)
    {
        maxHealth += value;
    }
    @Override
    public float getAttackSpeed()
    {
        return attackSpeed;
    }
    @Override
    public void setAttackSpeed(float value)
    {
        attackSpeed =  value;
    }
    @Override
    public void addAttackSpeed(float value)
    {
        attackSpeed += value;
    }
    @Override
    public int getMaxMana()
    {
        return mana;
    }
    @Override
    public void setMaxMana(int value)
    {
        mana = value;
    }
    @Override
    public void addMaxMana(int value)
    {
        mana += value;
    }
    @Override
    public int getCurrentMana()
    {
        return currentMana;
    }
    @Override
    public void setCurrentMana(int value)
    {
        currentMana = Math.min(value, mana);
    }
    @Override
    public void addCurrentMana(int value)
    {
        if (currentMana+value < mana)
        {
            currentMana += value;
        }
        else
        {
            currentMana = mana;
        }
    }
    @Override
    public boolean consumeMana(int amount)
    {
        if (currentMana > amount)
        {
            currentMana -= amount;
            return true;
        }
        return false;
    }
    @Override
    public float getMoveSpeedBonus()
    {
        return moveSpeedBonus;
    }
    @Override
    public void setMoveSpeedBonus(float value)
    {
        moveSpeedBonus = value;
    }
    @Override
    public void addMoveSpeedBonus(float value)
    {
        moveSpeedBonus += value;
    }
    @Override
    public float getRecoverHealthSpeedBonus()
    {
        return recoverHealthSpeedBonus;
    }
    @Override
    public void setRecoverHealthSpeedBonus(float value)
    {
        recoverHealthSpeedBonus = value;
    }
    @Override
    public void addRecoverHealthSpeedBonus(float value)
    {
        recoverHealthSpeedBonus += value;
    }
    @Override
    public float getRecoverManaSpeedBonus()
    {
        return recoverManaSpeedBonus;
    }
    @Override
    public void setRecoverManaSpeedBonus(float value)
    {
        recoverManaSpeedBonus = value;
    }
    @Override
    public void addRecoverManaSpeedBonus(float value)
    {
        recoverManaSpeedBonus += value;
    }
    @Override
    public int getArmor()
    {
        return armor;
    }

    @Override
    public void setArmor(int value)
    {
        armor = value;
    }
    @Override
    public void addArmor(int value)
    {
        armor += value;
    }
    @Override
    public float getDamageReduction()
    {
        return damageReduction;
    }
    @Override
    public void setDamageReduction(float value)
    {
        damageReduction = value;
    }
    @Override
    public void addDamageReduction(float value)
    {
        damageReduction += value;
    }
    @Override
    public float getPenetrationResistance()
    {
        return penetrationResistance;
    }
    @Override
    public void setPenetrationResistance(float value)
    {
        penetrationResistance  = value;
    }
    @Override
    public void addPenetrationResistance(float value)
    {
        penetrationResistance  += value;
    }
    @Override
    public float getPenetrationResistanceCoefficient()
    {
        return penetrationResistanceCoefficient;
    }
    @Override
    public void setPenetrationResistanceCoefficient(float value)
    {
        penetrationResistanceCoefficient = value;
    }
    @Override
    public void addPenetrationResistanceCoefficient(float value)
    {
        penetrationResistanceCoefficient += value;
    }
    @Override
    public float getArmorToughness()
    {
        return armorToughness;
    }
    @Override
    public void setArmorToughness(float value)
    {
        armorToughness = value;
    }
    @Override
    public void addArmorToughness(float value)
    {
        armorToughness += value;
    }

    @Override
    public void copyFrom(IPlayerAttributes source)
    {
        this.meleeDamageValueBonus = source.getMeleeDamageValueBonus();
        this.meleeDamagePercentBonus = source.getMeleeDamagePercentBonus();
        this.meleeCriticalDamageBonus = source.getMeleeCriticalDamageBonus();
        this.rangedDamageValueBonus = source.getRangedDamageValueBonus();
        this.rangedDamagePercentBonus = source.getRangedDamagePercentBonus();
        this.rangedCriticalDamageBonus = source.getRangedCriticalDamageBonus();
        this.magicDamageValueBonus = source.getMagicDamageValueBonus();
        this.magicDamagePercentBonus = source.getMagicDamagePercentBonus();
        this.magicCriticalDamageBonus = source.getMagicCriticalDamageBonus();
        this.criticalHitRate = source.getCriticalHitRate();
        this.baseDamagePercentBonus = source.getBaseDamagePercentBonus();
        this.maxHealth = source.getMaxHealthBonus();
        this.attackSpeed = source.getAttackSpeed();
        this.mana = source.getMaxMana();
        this.moveSpeedBonus = source.getMoveSpeedBonus();
        this.recoverHealthSpeedBonus  = source.getRecoverHealthSpeedBonus();
        this.recoverManaSpeedBonus = source.getRecoverManaSpeedBonus();
        this.armor = source.getArmor();
        this.damageReduction = source.getDamageReduction();
        this.penetrationResistance = source.getPenetrationResistance();
        this.penetrationResistanceCoefficient = source.getPenetrationResistanceCoefficient();
        this.armorToughness = source.getArmorToughness();
        this.currentMana = source.getCurrentMana();
    }

    @Override
    public void tick()
    {
        if (regenCooldown-- <= 0)
        {
            if (currentMana < mana)
            {
                currentMana = Math.min(mana, currentMana + 10);
            }
            regenCooldown = 20;
        }
    }

    @Override
    public CompoundTag serializeNBT(HolderLookup.Provider provider)
    {
        CompoundTag tag = new CompoundTag();
        tag.putFloat("meleeDamageValueBonus", meleeDamageValueBonus);
        tag.putFloat("meleeDamagePercentBonus", meleeDamagePercentBonus);
        tag.putFloat("meleeCriticalDamageBonus", meleeCriticalDamageBonus);
        tag.putFloat("rangedDamageValueBonus", rangedDamageValueBonus);
        tag.putFloat("rangedDamagePercentBonus", rangedDamagePercentBonus);
        tag.putFloat("rangedCriticalDamageBonus", rangedCriticalDamageBonus);
        tag.putFloat("magicDamageValueBonus", magicDamageValueBonus);
        tag.putFloat("magicDamagePercentBonus", magicDamagePercentBonus);
        tag.putFloat("magicCriticalDamageBonus", magicCriticalDamageBonus);
        tag.putFloat("criticalHitRate", criticalHitRate);
        tag.putFloat("baseDamagePercentBonus", baseDamagePercentBonus);
        tag.putInt("maxHealth", maxHealth);
        tag.putFloat("attackSpeed", attackSpeed);
        tag.putInt("mana", mana);
        tag.putFloat("moveSpeedBonus", moveSpeedBonus);
        tag.putFloat("recoverHealthSpeedBonus", recoverHealthSpeedBonus);
        tag.putFloat("recoverManaSpeedBonus", recoverManaSpeedBonus);
        tag.putInt("armor", armor);
        tag.putFloat("damageReduction", damageReduction);
        tag.putFloat("penetrationResistance", penetrationResistance);
        tag.putFloat("penetrationResistanceCoefficient", penetrationResistanceCoefficient);
        tag.putFloat("armorToughness", armorToughness);
        tag.putInt("currentMana", currentMana);

        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag)
    {
        this.meleeDamageValueBonus = compoundTag.getFloat("meleeDamageValueBonus");
        this.meleeDamagePercentBonus = compoundTag.getFloat("meleeDamagePercentBonus");
        this.meleeCriticalDamageBonus = compoundTag.getFloat("meleeCriticalDamageBonus");
        this.rangedDamageValueBonus = compoundTag.getFloat("rangedDamageValueBonus");
        this.rangedDamagePercentBonus = compoundTag.getFloat("rangedDamagePercentBonus");
        this.rangedCriticalDamageBonus = compoundTag.getFloat("rangedCriticalDamageBonus");
        this.magicDamageValueBonus = compoundTag.getFloat("magicDamageValueBonus");
        this.magicDamagePercentBonus = compoundTag.getFloat("magicDamagePercentBonus");
        this.magicCriticalDamageBonus = compoundTag.getFloat("magicCriticalDamageBonus");
        this.criticalHitRate = compoundTag.getFloat("criticalHitRate");
        this.baseDamagePercentBonus = compoundTag.getFloat("baseDamagePercentBonus");
        this.maxHealth = compoundTag.getInt("maxHealth");
        this.attackSpeed = compoundTag.getFloat("attackSpeed");
        this.mana = compoundTag.getInt("mana");
        this.moveSpeedBonus = compoundTag.getFloat("moveSpeedBonus");
        this.recoverHealthSpeedBonus = compoundTag.getFloat("recoverHealthSpeedBonus");
        this.recoverManaSpeedBonus = compoundTag.getFloat("recoverManaSpeedBonus");
        this.armor = compoundTag.getInt("armor");
        this.damageReduction = compoundTag.getFloat("damageReduction");
        this.penetrationResistance = compoundTag.getFloat("penetrationResistance");
        this.penetrationResistanceCoefficient = compoundTag.getFloat("penetrationResistanceCoefficient");
        this.armorToughness = compoundTag.getFloat("armorToughness");
        this.currentMana = compoundTag.getInt("currentMana");
    }
}
