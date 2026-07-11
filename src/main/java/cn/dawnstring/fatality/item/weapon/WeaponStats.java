package cn.dawnstring.fatality.item.weapon;

public record WeaponStats(
        float baseDamage,
        float critRate,
        float critDamage,
        float fluctuation,
        float attSpeed,
        WeaponType weaponType
) { }
