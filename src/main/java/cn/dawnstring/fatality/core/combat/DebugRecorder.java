package cn.dawnstring.fatality.core.combat;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DebugRecorder
{
    private static final Set<UUID> activePlayers = ConcurrentHashMap.newKeySet();

    private static final Map<UUID, Map<UUID, DebugRecord>> pending = new ConcurrentHashMap<>();
    private static final Map<UUID, List<DebugRecord>> completed = new ConcurrentHashMap<>();

    public static boolean toggle(UUID player)
    {
        if (!activePlayers.add(player))
        {
            activePlayers.remove(player);
            return false;
        }
        return true;
    }

    public static boolean isActive(UUID player)
    {
        return activePlayers.contains(player);
    }

    public static void stageAttack(UUID owner, UUID targetUuid, DebugRecord partial)
    {
        if (!activePlayers.contains(owner)) return;
        pending.computeIfAbsent(owner, k -> new ConcurrentHashMap<>())
               .put(targetUuid, partial);
    }

    public static void stageArmor(UUID owner, UUID targetUuid,
                                  float armorOut, boolean isPenetrated)
    {
        var ownerPending = pending.get(owner);
        if (ownerPending == null) return;

        DebugRecord partial = ownerPending.remove(targetUuid);
        if (partial == null) return;

        DebugRecord complete = new DebugRecord(
                partial.tick(),
                partial.targetName(),
                partial.weaponType(),
                partial.originalDamage(),
                partial.handlerOut(),
                armorOut,
                partial.isCrit(),
                isPenetrated,
                partial.debugAttrs()
        );

        completed.computeIfAbsent(owner, k -> new ArrayList<>()).add(complete);
    }

    public static List<DebugRecord> flush(UUID owner)
    {
        List<DebugRecord> result = completed.remove(owner);
        pending.remove(owner);
        return result != null ? result : Collections.emptyList();
    }

    public static void clear(UUID owner)
    {
        completed.remove(owner);
        pending.remove(owner);
    }
}
