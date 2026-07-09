package cn.dawnstring.fatality.core.ability;

import cn.dawnstring.fatality.core.combat.DamageHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/**
 * 饰品能力接口。
 * <p>AccessoryItem 子类可通过实现此接口来获得特殊机制，无需改动事件系统。
 * <p>所有方法均为 default，子类只需 override 需要的触发点。
 */
public interface Ability
{
    //判断是否暴击
    boolean isCrit = Boolean.TRUE.equals(DamageHandler.LAST_HIT_CRIT.get());
    /**
     * 造成伤害前回调。
     * @param player 攻击者
     * @param target 目标
     * @param amount 原始伤害值
     * @return 修改后的伤害值
     */
    default float modifyOutgoingDamage(Player player, LivingEntity target, float amount)
    {
        return amount;
    }

    /**
     * 击杀目标时回调。
     */
    default void onKill(Player player, LivingEntity target)
    {
    }

    /**
     * 受到伤害时回调。
     * @param player 被攻击者
     * @param source 伤害源
     * @param amount 原始伤害值
     * @return 修改后的伤害值；返回 0 表示完全闪避/免疫
     */
    default float onHurt(Player player, DamageSource source, float amount)
    {
        return amount;
    }

    /**
     * 成功命中目标时回调（伤害已应用后）。
     * @param player 攻击者
     * @param target 被命中的目标
     * @param amount 实际造成的伤害值
     */
    default void onHit(Player player, LivingEntity target, float amount)
    {
    }

    /**
     * 每 tick 回调（20次/秒）。
     */
    default void tick(Player player)
    {
    }
}
