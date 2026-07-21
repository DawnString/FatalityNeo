package cn.dawnstring.fatality.item.weapon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * 武器命中后触发的被动效果。
 */
public interface WeaponPassive
{
    void onHit(WeaponHitContext ctx);

    record WeaponHitContext(
            Player attacker,
            LivingEntity target,
            ItemStack weapon,
            float damage,
            boolean isCrit
    ) {}
}
