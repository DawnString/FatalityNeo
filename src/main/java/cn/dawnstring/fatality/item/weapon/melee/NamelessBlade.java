package cn.dawnstring.fatality.item.weapon.melee;

import cn.dawnstring.fatality.core.boss.BossKillData;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.UniqueItemType;
import cn.dawnstring.fatality.item.weapon.*;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.List;

@AutoItem(itemId = "nameless_blade", category = ItemCategory.WEAPON)
public class NamelessBlade extends WeaponItem
{
    public NamelessBlade()
    {
        super(
                new Item.Properties().stacksTo(1),
                STATS,
                List.of(),
                null
        );

        setUniqueItemTypeDes(UniqueItemType.SUPPORTER_ITEM);
        setUniqueDes(Component.translatable("item.fatality.nameless_blade.unique"));
    }

    private static final WeaponStats STATS = new WeaponStats(
            350.0f,
            0.15f,
            0.20f,
            0.05f,
            0.45f,
            WeaponType.MELEE,
            AttackMode.SWING,
            0,
            6
    );

    // ===== 机制一：无名 =====
    // 每击败一个 Boss，伤害 +0.5%（乘算倍率）
    //
    // ===== 机制二：斩杀（击败 WITHER 后解锁）=====
    // 目标血量低于 30% 时，伤害翻倍
    //
    // ===== 机制三：终结（击败 NIGHTMARE_OF_FINALITY 后解锁）=====
    // 每次攻击附加 目标已损生命值 × 10% 的固定伤害
    // =========================

    @Override
    public float getDamageMultiplier(Player player, LivingEntity target)
    {
        var data = BossKillData.get(player.level());
        if (data == null) return 1.0f;

        // 无名：每个 Boss +0.5%
        float mult = 1.0f + 0.005f * data.countDefeated();

        // 斩杀：击败 Wither → 目标低于 30% 血量时翻倍
        if (data.hasDefeated("wither")
                && target.getHealth() / target.getMaxHealth() < 0.3f)
            mult *= 2.0f;

        return mult;
    }

    @Override
    public float getBonusDamage(Player player, LivingEntity target)
    {
        var data = BossKillData.get(player.level());
        if (data == null) return 0f;

        // 终结：击败 Nightmare of Finality → 附加已损生命 × 10%
        if (data.hasDefeated("nightmare_of_finality"))
        {
            float missing = target.getMaxHealth() - target.getHealth();
            return missing * 0.1f;
        }

        return 0f;
    }
}
