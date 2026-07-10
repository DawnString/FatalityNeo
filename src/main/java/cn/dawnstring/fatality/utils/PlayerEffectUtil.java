package cn.dawnstring.fatality.utils;

import cn.dawnstring.fatality.network.SyncEffectPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerEffectUtil
{
    private static final Map<UUID, EffectData> ACTIVE_EFFECTS = new ConcurrentHashMap<>();

    // === 预设脉冲速度 ===
    public static final float PULSE_NONE   = 0;
    public static final float PULSE_SLOW   = 0.03f;
    public static final float PULSE_NORMAL = 0.05f;
    public static final float PULSE_FAST   = 0.12f;
    public static final float PULSE_RAPID  = 0.2f;

    public record EffectData(ResourceLocation texture, int color, float pulseSpeed)
    {
        public EffectData {}
    }

    /**
     * 给玩家添加表面光效
     * @param texture   null = 使用噪声纹理（能量护盾效果）
     * @param color     ARGB 色值，如 0x88FF4444 = 半透明红
     * @param pulseSpeed 呼吸速度，0=静态
     */
    public static void applyGlow(Player player, ResourceLocation texture, int color, float pulseSpeed)
    {
        if (player.level().isClientSide())
        {
            ACTIVE_EFFECTS.put(player.getUUID(), new EffectData(texture, color, pulseSpeed));
        }
        else if (player instanceof ServerPlayer sp)
        {
            PacketDistributor.sendToPlayer(sp,
                    new SyncEffectPayload(player.getUUID(), texture, color, pulseSpeed));
        }
    }

    /** 静态护盾（长久显示），使用噪声纹理，不呼吸 */
    public static void applyStaticShield(Player player, int color)
    {
        applyGlow(player, null, color, PULSE_NONE);
    }

    /** 缓慢呼吸护盾，使用噪声纹理 */
    public static void applySlowShield(Player player, int color)
    {
        applyGlow(player, null, color, PULSE_SLOW);
    }

    /** 正常呼吸护盾，使用噪声纹理 */
    public static void applyBreathingShield(Player player, int color)
    {
        applyGlow(player, null, color, PULSE_NORMAL);
    }

    /** 快速闪烁护盾，使用噪声纹理 */
    public static void applyFastShield(Player player, int color)
    {
        applyGlow(player, null, color, PULSE_FAST);
    }

    /** 急速脉冲护盾，使用噪声纹理 */
    public static void applyRapidShield(Player player, int color)
    {
        applyGlow(player, null, color, PULSE_RAPID);
    }

    /** 移除玩家表面光效 */
    public static void removeGlow(Player player)
    {
        applyGlow(player, null, 0, 0);
    }

    public static void apply(UUID uuid, ResourceLocation texture, int color, float pulseSpeed)
    {
        ACTIVE_EFFECTS.put(uuid, new EffectData(texture, color, pulseSpeed));
    }

    public static void remove(UUID uuid)
    {
        ACTIVE_EFFECTS.remove(uuid);
    }

    public static void clear()
    {
        ACTIVE_EFFECTS.clear();
    }

    public static int getRenderColor(UUID uuid, float ageInTicks)
    {
        EffectData data = ACTIVE_EFFECTS.get(uuid);
        if (data == null) return 0;

        float baseAlpha = ((data.color() >> 24) & 0xFF) / 255.0f;
        if (baseAlpha < 0.01f) return 0;

        float alpha = baseAlpha;
        if (data.pulseSpeed() > 0)
            alpha *= (float)((Math.sin(ageInTicks * data.pulseSpeed()) + 1.0) * 0.5);

        if (alpha < 0.01f) return 0;

        int r = (data.color() >> 16) & 0xFF;
        int g = (data.color() >> 8) & 0xFF;
        int b = data.color() & 0xFF;

        return ((int)(alpha * 255) << 24) | (r << 16) | (g << 8) | b;
    }

    public static ResourceLocation getRenderTexture(UUID uuid, ResourceLocation fallback)
    {
        EffectData data = ACTIVE_EFFECTS.get(uuid);
        if (data == null || data.texture() == null) return fallback;
        return data.texture();
    }
}
