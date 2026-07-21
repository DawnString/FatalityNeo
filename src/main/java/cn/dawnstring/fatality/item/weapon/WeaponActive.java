package cn.dawnstring.fatality.item.weapon;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public abstract class WeaponActive
{
    private final int cooldownTicks;
    private final int manaCost;

    public WeaponActive(int cooldownTicks, int manaCost)
    {
        this.cooldownTicks = cooldownTicks;
        this.manaCost = manaCost;
    }

    public WeaponActive(int cooldownTicks)
    {
        this(cooldownTicks, 0);
    }

    public int getCooldownTicks()
    {
        return cooldownTicks;
    }

    public int getManaCost()
    {
        return manaCost;
    }

    public abstract String getNameKey();

    public abstract void execute(Player player, Level level);
}
