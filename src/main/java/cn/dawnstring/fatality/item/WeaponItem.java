package cn.dawnstring.fatality.item;

import net.minecraft.world.item.Item;

public abstract class WeaponItem extends Item
{
    private final WeaponStats stats;

    public WeaponItem(Properties properties, WeaponStats stats)
    {
        super(properties);
        this.stats = stats;
    }

    public WeaponStats getWeaponStats()
    {
        return stats;
    }
}
