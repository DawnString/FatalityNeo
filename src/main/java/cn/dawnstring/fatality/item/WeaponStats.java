package cn.dawnstring.fatality.item;

public record WeaponStats(
        float baseDamage,
        float critRate,
        float critDamage,
        float fluctuation,
        float attSpeed,
        WeaponType weaponType
) { }
