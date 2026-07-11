package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.core.combat.RegenSystem;
import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.utils.PlayerEffectUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "shadow_armor", category = ItemCategory.ACCESSORY)
public class ShadowArmor extends AccessoryItem implements Ability
{
    /**
     * MAX_SHIELD 最大盾量
     * MANA_PER_SHIELD 每点盾要多少魔力
     * PAUSE_TICKS 护盾暂停恢复的时间
     * BROKEN_COOLDOWN 护盾被打破时再次形成护盾的冷却时间
     * RECOVER_INTERVAL 多久恢复一次护盾
     * SHIELD_COLOR 护盾颜色
     */
    private static final float MAX_SHIELD = 100;
    private static final int MANA_PER_SHIELD = 2;
    private static final int PAUSE_TICKS = 100;
    private static final int BROKEN_COOLDOWN = 600;
    private static final int RECOVER_INTERVAL = 20;
    private static final int SHIELD_COLOR = 0x884444FF;

    private static final Map<UUID, ShieldData> shieldMap = new ConcurrentHashMap<>();
    private static final Map<UUID, Boolean> shieldVisible = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> lastTick = new ConcurrentHashMap<>();

    private record ShieldData(float shield, int recoveryCooldown, int brokenCooldown, int recoverTimer) {}

    public ShadowArmor()
    {
        super(List.of(
                new StatModifier("armor", 5),
                new StatModifier("mana", 40)
        ));
    }

    @Override
    public void tick(Player player)
    {
        // 防止被 AccessoryItem.tick() 和 Ability.tick() 双重调用
        if (player.tickCount == lastTick.getOrDefault(player.getUUID(), -1))
            return;
        lastTick.put(player.getUUID(), player.tickCount);

        ShieldData data = shieldMap.get(player.getUUID());
        if (data == null)
        {
            // 首次装备或重新装备，初始化护盾开始恢复
            shieldMap.put(player.getUUID(), new ShieldData(0, 0, 0, 0));
            data = shieldMap.get(player.getUUID());
        }

        int recoveryCd = Math.max(0, data.recoveryCooldown - 1);
        int brokenCd = Math.max(0, data.brokenCooldown - 1);
        float shield;
        int timer;

        if (brokenCd > 0)
        {
            shield = 0;
            timer = 0;
        }
        else if (recoveryCd > 0)
        {
            shield = data.shield;
            timer = 0;
        }
        else
        {
            shield = data.shield;
            timer = data.recoverTimer + 1;

            if (shield < MAX_SHIELD && timer >= RECOVER_INTERVAL)
            {
                if (RegenSystem.consumeMana(player, MANA_PER_SHIELD))
                    shield = Math.min(MAX_SHIELD, shield + 1);
                timer = 0;
            }
        }

        shieldMap.put(player.getUUID(), new ShieldData(shield, recoveryCd, brokenCd, timer));
        updateEffect(player, shield);
    }

    private void updateEffect(Player player, float shield)
    {
        UUID uuid = player.getUUID();

        if (shield > 0)
        {
            if (!Boolean.TRUE.equals(shieldVisible.get(uuid)))
            {
                PlayerEffectUtil.applyStaticShield(player, SHIELD_COLOR);
                shieldVisible.put(uuid, true);
            }
        }
        else
        {
            if (Boolean.TRUE.equals(shieldVisible.get(uuid)))
            {
                PlayerEffectUtil.removeGlow(player);
                shieldVisible.put(uuid, false);
            }
        }
    }

    private void removeEffect(Player player)
    {
        UUID uuid = player.getUUID();
        if (Boolean.TRUE.equals(shieldVisible.remove(uuid)))
            PlayerEffectUtil.removeGlow(player);
    }

    @Override
    public void onUnequipped(Player player)
    {
        shieldMap.remove(player.getUUID());
        removeEffect(player);
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        ShieldData data = shieldMap.get(player.getUUID());

        if (data == null || data.shield <= 0)
        {
            shieldMap.put(player.getUUID(), new ShieldData(0, PAUSE_TICKS, 0, 0));
            return amount;
        }

        float absorbed = Math.min(data.shield, amount);
        float remaining = amount - absorbed;
        float newShield = data.shield - absorbed;

        if (newShield <= 0)
        {
            shieldMap.put(player.getUUID(), new ShieldData(0, 0, BROKEN_COOLDOWN, 0));
        }
        else
        {
            shieldMap.put(player.getUUID(), new ShieldData(newShield, PAUSE_TICKS, 0, 0));
        }

        return remaining;
    }
}
