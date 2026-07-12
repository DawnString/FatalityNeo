package cn.dawnstring.fatality.item.accessory;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.core.input.PlayerInputState;
import cn.dawnstring.fatality.utils.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BaseWingItem extends AccessoryItem implements Ability
{
    private static final Map<UUID, Integer> FLIGHT_TIME = new ConcurrentHashMap<>();
    private static final Map<UUID, Integer> PROCESSED_TICK = new ConcurrentHashMap<>();

    private static final String TAG_FLIGHT_TIME = "FatalityWingFlightTime";

    private final WingStats wingStats;

    public BaseWingItem(WingStats wingStats, List<StatModifier> modifiers)
    {
        super(modifiers);
        this.wingStats = wingStats;
    }

    public WingStats getWingStats()
    {
        return wingStats;
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        if (source.is(DamageTypeTags.IS_FALL))
        {
            player.fallDistance = 0;
            return 0;
        }
        return amount;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("§7").append(Component.translatable("wing.fatality.maxHorizontalSpeed").getString()).append(": §e").append(String.format("%.1f", wingStats.maxHorizontalSpeed() * 20)).append(" m/s\n");
        sb.append("§7").append(Component.translatable("wing.fatality.horizontalAcceleration").getString()).append(": §e").append(String.format("%.1f", wingStats.horizontalAcceleration() * 20)).append(" m/s²\n");
        sb.append("§7").append(Component.translatable("wing.fatality.maxVerticalSpeed").getString()).append(": §e").append(String.format("%.1f", wingStats.maxVerticalSpeed() * 20)).append(" m/s\n");
        sb.append("§7").append(Component.translatable("wing.fatality.upwardAcceleration").getString()).append(": §e").append(String.format("%.1f", wingStats.upwardAcceleration() * 20)).append(" m/s²\n");
        sb.append("§7").append(Component.translatable("wing.fatality.glideSpeed").getString()).append(": §e").append(String.format("%.1f", wingStats.glideSpeed() * 20)).append(" m/s\n");
        sb.append("§7").append(Component.translatable("wing.fatality.maxFlightTime").getString()).append(": §e").append(String.format("%.1f", wingStats.maxFlightTime() / 20.0f)).append(" s\n");
        if (wingStats.horizontalDrag() > 0)
            sb.append("§7").append(Component.translatable("wing.fatality.horizontalDrag").getString()).append(": §e").append(String.format("%.2f", wingStats.horizontalDrag()));
        TooltipHelper.addDescriptiveTooltip(stack, tooltip, flag, null, sb.toString(), false);
    }

    public static int getRemainingFlightTime(Player player)
    {
        Integer time = FLIGHT_TIME.get(player.getUUID());
        return time != null ? time : 0;
    }

    @Override
    public void tick(Player player)
    {
        super.tick(player);

        if (player.level().isClientSide())
        {
            tickClient(player);
            return;
        }

        serverTick(player);
    }

    private void serverTick(Player player)
    {
        //去重：同一 tick 多个翅膀只处理一次
        Integer lastTick = PROCESSED_TICK.get(player.getUUID());
        if (lastTick != null && lastTick == player.tickCount)
            return;
        PROCESSED_TICK.put(player.getUUID(), player.tickCount);

        //创造/旁观模式跳过
        if (player.isCreative() || player.isSpectator()) return;

        //水中/岩浆中禁用飞行
        if (player.isInWater() || player.isInLava())
        {
            FLIGHT_TIME.remove(player.getUUID());
            return;
        }

        //加载或初始化飞行时间
        int remaining = loadFlightTime(player);

        //地面：恢复飞行时间，1秒恢复完全
        if (player.onGround())
        {
            remaining = Math.min(wingStats.maxFlightTime(), remaining + Math.max(1, wingStats.maxFlightTime() / 20));
            saveFlightTime(player, remaining);
            return;
        }

        //空中：检测跳跃键
        boolean jumping = PlayerInputState.isJumping(player);
        if (!jumping)
        {
            //自由落体，不干预
            saveFlightTime(player, remaining);
            return;
        }

        //按住空格：飞行或滑翔
        Vec3 motion = player.getDeltaMovement();

        //水平控制
        motion = applyHorizontalControl(player, motion);

        if (remaining > 0)
        {
            //飞行：上升
            remaining--;
            motion = new Vec3(motion.x,
                    Math.min(motion.y + wingStats.upwardAcceleration(), wingStats.maxVerticalSpeed()),
                    motion.z);
        }
        else
        {
            //滑翔：缓降
            motion = new Vec3(motion.x,
                    Math.max(motion.y - 0.08, -wingStats.glideSpeed()),
                    motion.z);
        }

        player.setDeltaMovement(motion);
        player.fallDistance = 0;
        player.hurtMarked = true;

        saveFlightTime(player, remaining);
    }

    private Vec3 applyHorizontalControl(Player player, Vec3 motion)
    {
        float forward = PlayerInputState.getForwardImpulse(player);
        float strafe = PlayerInputState.getLeftImpulse(player);

        double yawRad = Math.toRadians(player.getYRot());
        double sin = Math.sin(yawRad);
        double cos = Math.cos(yawRad);

        double ax = (-sin * forward + cos * strafe) * wingStats.horizontalAcceleration();
        double az = (cos * forward + sin * strafe) * wingStats.horizontalAcceleration();

        motion = motion.add(ax, 0, az);

        //无输入时减速
        if (forward == 0 && strafe == 0)
        {
            motion = motion.multiply(1.0 - wingStats.horizontalDrag(), 1.0, 1.0 - wingStats.horizontalDrag());
        }

        //水平速度钳制
        double hSpeed = Math.sqrt(motion.x * motion.x + motion.z * motion.z);
        if (hSpeed > wingStats.maxHorizontalSpeed())
        {
            double scale = wingStats.maxHorizontalSpeed() / hSpeed;
            motion = new Vec3(motion.x * scale, motion.y, motion.z * scale);
        }

        return motion;
    }

    private void tickClient(Player player)
    {
        //TODO 客户端视觉效果：粒子、翅膀动画
    }

    private int loadFlightTime(Player player)
    {
        //先从缓存读
        Integer cached = FLIGHT_TIME.get(player.getUUID());
        if (cached != null)
            return Math.min(cached, wingStats.maxFlightTime());

        //缓存未命中，从持久化数据加载
        var data = player.getPersistentData();
        int stored = data.getInt(TAG_FLIGHT_TIME);
        if (stored > 0)
        {
            stored = Math.min(stored, wingStats.maxFlightTime());
        }
        else if (!data.contains(TAG_FLIGHT_TIME))
        {
            //首次使用翅膀
            stored = wingStats.maxFlightTime();
        }
        FLIGHT_TIME.put(player.getUUID(), stored);
        return stored;
    }

    private void saveFlightTime(Player player, int time)
    {
        FLIGHT_TIME.put(player.getUUID(), time);
        player.getPersistentData().putInt(TAG_FLIGHT_TIME, time);
    }

    @Override
    public void onRemove(Player player)
    {
        UUID uuid = player.getUUID();
        FLIGHT_TIME.remove(uuid);
        PROCESSED_TICK.remove(uuid);
    }
}
