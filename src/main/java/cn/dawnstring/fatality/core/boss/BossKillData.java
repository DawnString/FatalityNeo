package cn.dawnstring.fatality.core.boss;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

/**
 * 世界存档级 Boss 击败记录。
 * 存储于 data/fatality_boss_kills.dat（覆盖世界维度）。
 */
public class BossKillData extends SavedData
{
    private static final String DATA_NAME = "fatality_boss_kills";

    private final Set<String> defeatedBosses = new HashSet<>();

    // ===== 工厂 =====

    public static BossKillData get(Level level)
    {
        if (level.isClientSide()) return null;

        ServerLevel overworld = level.getServer().getLevel(Level.OVERWORLD);
        if (overworld == null) return null;

        return overworld.getDataStorage().computeIfAbsent(
                new SavedData.Factory<>(
                        () -> new BossKillData(),
                        (tag, registries) -> new BossKillData(tag)
                ),
                DATA_NAME
        );
    }

    public BossKillData() {}

    public BossKillData(CompoundTag tag)
    {
        ListTag list = tag.getList("defeated", Tag.TAG_STRING);
        for (int i = 0; i < list.size(); i++)
            defeatedBosses.add(list.getString(i));
    }

    // ===== 序列化 =====

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries)
    {
        var list = new ListTag();
        for (String id : defeatedBosses)
            list.add(StringTag.valueOf(id));
        tag.put("defeated", list);
        return tag;
    }

    // ===== 查询 =====

    public boolean hasDefeated(String bossId)
    {
        return defeatedBosses.contains(bossId);
    }

    public int countDefeated()
    {
        return defeatedBosses.size();
    }

    public void markDefeated(String bossId)
    {
        if (defeatedBosses.add(bossId))
            setDirty();
    }

    /**
     * 从实体类型推断 Boss ID。
     * 用于 {@link net.neoforged.neoforge.event.entity.living.LivingDeathEvent}。
     */
    @Nullable
    public static String resolveBossId(LivingEntity entity)
    {
        if (entity.getType() == EntityType.WITHER)
            return "wither";

        // TODO: 注册 NIGHTMARE_OF_FINALITY 的 EntityType 后加入检测
        // if (entity.getType() == ModEntityTypes.NIGHTMARE_OF_FINALITY.get())
        //     return "nightmare_of_finality";

        return null;
    }
}
