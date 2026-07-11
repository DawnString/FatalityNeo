package cn.dawnstring.fatality.core.combat;

import cn.dawnstring.fatality.core.capability.PlayerAttributes;
import cn.dawnstring.fatality.core.capability.PlayerAttributesProvider;
import cn.dawnstring.fatality.item.armor.ArmorStats;
import cn.dawnstring.fatality.item.armor.BaseArmorItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ArmorStatManager
{
    private record ArmorSnapshot(float damageReduction, float penetrationResistance, float penetrationResistanceCoefficient) {}

    private static final Map<UUID, ArmorSnapshot> cached = new ConcurrentHashMap<>();

    public static void tick(Player player)
    {
        if (player.level().isClientSide())
            return;

        ArmorSnapshot snap = snapshot(player);
        ArmorSnapshot prev = cached.get(player.getUUID());
        if (snap.equals(prev))
            return;

        PlayerAttributes attrs = PlayerAttributesProvider.getAttributes(player);

        // 撤销旧值
        if (prev != null)
        {
            attrs.addDamageReduction(-prev.damageReduction());
            attrs.addPenetrationResistance(-prev.penetrationResistance());
            attrs.addPenetrationResistanceCoefficient(-prev.penetrationResistanceCoefficient());
        }

        // 添加新值
        attrs.addDamageReduction(snap.damageReduction());
        attrs.addPenetrationResistance(snap.penetrationResistance());
        attrs.addPenetrationResistanceCoefficient(snap.penetrationResistanceCoefficient());

        cached.put(player.getUUID(), snap);
    }

    private static ArmorSnapshot snapshot(Player player)
    {
        float dr = 0, pr = 0, prc = 0;
        for (ItemStack stack : player.getInventory().armor)
        {
            if (stack.getItem() instanceof BaseArmorItem item)
            {
                ArmorStats s = item.getArmorStats();
                dr += s.damageReduction();
                pr += s.penetrationResistance();
                prc += s.penetrationResistanceCoefficient();
            }
        }
        return new ArmorSnapshot(dr, pr, prc);
    }
}
