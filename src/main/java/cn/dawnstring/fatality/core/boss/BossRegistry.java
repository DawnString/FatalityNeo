package cn.dawnstring.fatality.core.boss;

import java.util.*;

public class BossRegistry
{
    private static final Map<String, BossData> BOSSES = new LinkedHashMap<>();

    // ===== 前期 =====
    public static final BossData WARDEN                = reg("warden",                BossPhase.EARLY, 2500,  5000,  "overworld");
    public static final BossData IMPERIAL_COMMANDER    = reg("imperial_commander",    BossPhase.EARLY, 4000,  8000,  "overworld");
    public static final BossData CALAMITY_MAGE         = reg("calamity_mage",         BossPhase.EARLY, 6500,  13000, "overworld");
    public static final BossData MECHANICAL_WRECKAGE   = reg("mechanical_wreckage",   BossPhase.EARLY, 9000,  18000, "overworld");
    public static final BossData SILENT_STATUE         = reg("silent_statue",         BossPhase.EARLY, 12000, 24000, "overworld");
    public static final BossData THOUSAND_FACES        = reg("thousand_faces",        BossPhase.EARLY, 15000, 30000, "overworld");
    public static final BossData PRIMORDIAL_WRAITH     = reg("primordial_wraith",     BossPhase.EARLY, 20000, 40000, "overworld");

    // ===== 中期 =====
    public static final BossData EMBER_SHADOW          = reg("ember_shadow",          BossPhase.MID,   30000, 60000,  "overworld");
    public static final BossData EXILE_KNIGHT          = reg("exile_knight",          BossPhase.MID,   42000, 84000,  "overworld");
    public static final BossData FLESH_AMALGAM         = reg("flesh_amalgam",         BossPhase.MID,   55000, 110000, "overworld");
    public static final BossData BLOODTHIRSTY_GEL      = reg("bloodthirsty_gel",      BossPhase.MID,   68000, 136000, "overworld");
    public static final BossData CAST_IRON_GUARD       = reg("cast_iron_guard",       BossPhase.MID,   80000, 160000, "overworld");
    public static final BossData RESTLESS_EYE          = reg("restless_eye",          BossPhase.MID,   92000, 180000, "overworld");
    public static final BossData HELL_LORD             = reg("hell_lord",             BossPhase.MID,   110000, 210000, "the_nether");
    public static final BossData GOD_WRAITH            = reg("god_wraith",            BossPhase.MID,   135000, 260000, "overworld");
    public static final BossData RAGING_FLAME          = reg("raging_flame",          BossPhase.MID,   160000, 310000, "overworld");
    public static final BossData ENDER_DRAGON          = reg("ender_dragon",          BossPhase.MID,   200000, 380000, "the_end");
    public static final BossData WITHER                = reg("wither",                BossPhase.MID,   180000, 340000, "overworld");

    // ===== 后期 =====
    public static final BossData ENDER_SOUL            = reg("ender_soul",            BossPhase.LATE,  240000, 430000, "the_end");
    public static final BossData MEMORY_SERVANT        = reg("memory_servant",        BossPhase.LATE,  290000, 520000, "overworld");
    public static final BossData MECHANICAL_DRAGON_BONES = reg("mechanical_dragon_bones", BossPhase.LATE, 350000, 620000, "overworld");
    public static final BossData MOLTEN_WING_DRAGON    = reg("molten_wing_dragon",    BossPhase.LATE,  420000, 740000, "overworld");
    public static final BossData HOLY_FLAME_CALAMITY   = reg("holy_flame_calamity",   BossPhase.LATE,  500000, 880000, "overworld");
    public static final BossData SOUL_BURNING_WITCH    = reg("soul_burning_witch",    BossPhase.LATE,  600000, 1050000, "overworld");
    public static final BossData HUNTING_DRAGON        = reg("hunting_dragon",        BossPhase.LATE,  720000, 1250000, "overworld");
    public static final BossData MAGE_AFTERIMAGE       = reg("mage_afterimage",       BossPhase.LATE,  850000, 1450000, "overworld");
    public static final BossData NIGHTMARE_OF_FINALITY = reg("nightmare_of_finality", BossPhase.LATE,  1000000, 1700000, "overworld");

    private static BossData reg(String id, BossPhase phase, int normalHp, int expertHp, String dimension)
    {
        var data = new BossData(
                id,
                "boss.fatality." + id + ".name",
                "boss.fatality." + id + ".lore",
                "boss.fatality." + id + ".truth",
                normalHp, expertHp,
                phase, dimension
        );
        BOSSES.put(id, data);
        return data;
    }

    public static BossData get(String id)
    {
        return BOSSES.get(id);
    }

    public static Collection<BossData> getAll()
    {
        return Collections.unmodifiableCollection(BOSSES.values());
    }

    public static List<BossData> getByPhase(BossPhase phase)
    {
        return BOSSES.values().stream()
                .filter(b -> b.phase() == phase)
                .toList();
    }

    public static List<BossData> getByDimension(String dimension)
    {
        return BOSSES.values().stream()
                .filter(b -> b.dimension().equals(dimension))
                .toList();
    }

    public static boolean exists(String id)
    {
        return BOSSES.containsKey(id);
    }

    public static int count()
    {
        return BOSSES.size();
    }
}
