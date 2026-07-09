package cn.dawnstring.fatality.core.ability;

import cn.dawnstring.fatality.core.accessory.AccessoryManager;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Ability 调度器。
 * <p>遍历玩家饰品栏，将事件分发给实现了 {@link Ability} 接口的饰品。
 */
public class AbilitySystem
{
    private static void forEach(Player player, AbilityConsumer consumer)
    {
        var inv = AccessoryManager.getInventory(player);
        for (int i = 0; i < AccessoryManager.SLOT_COUNT; i++)
        {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() instanceof Ability ability)
                consumer.accept(ability, player);
        }
    }

    @FunctionalInterface
    private interface AbilityConsumer
    {
        void accept(Ability ability, Player player);
    }

    /**
     * 修改玩家造成的伤害。依次调用每个 Ability 的 modifyOutgoingDamage，链式累积。
     */
    public static float modifyOutgoingDamage(Player player, LivingEntity target, float amount)
    {
        float result = amount;
        var inv = AccessoryManager.getInventory(player);
        for (int i = 0; i < AccessoryManager.SLOT_COUNT; i++)
        {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() instanceof Ability ability)
                result = ability.modifyOutgoingDamage(player, target, result);
        }
        return result;
    }

    /**
     * 玩家击杀目标时回调。
     */
    public static void onKill(Player player, LivingEntity target)
    {
        forEach(player, (ability, p) -> ability.onKill(p, target));
    }

    /**
     * 玩家受到伤害时回调。
     * @return 修改后的伤害值；0 表示完全免疫
     */
    public static float onHurt(Player player, DamageSource source, float amount)
    {
        float result = amount;
        var inv = AccessoryManager.getInventory(player);
        for (int i = 0; i < AccessoryManager.SLOT_COUNT; i++)
        {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() instanceof Ability ability)
            {
                result = ability.onHurt(player, source, result);
                if (result <= 0)
                    return 0;
            }
        }
        return result;
    }

    /**
     * 玩家命中目标时回调。
     * @param amount 最终实际造成的伤害值
     */
    public static void onHit(Player player, LivingEntity target, float amount)
    {
        var inv = AccessoryManager.getInventory(player);
        for (int i = 0; i < AccessoryManager.SLOT_COUNT; i++)
        {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() instanceof Ability ability)
                ability.onHit(player, target, amount);
        }
    }

    /**
     * 每 tick 回调。
     */
    public static void tick(Player player)
    {
        forEach(player, Ability::tick);
    }
}
