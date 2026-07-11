package cn.dawnstring.fatality.item.accessory.offensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import cn.dawnstring.fatality.utils.ParticleUtil;
import cn.dawnstring.fatality.utils.RandomUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@AutoItem(itemId = "watchers_eye", category = ItemCategory.ACCESSORY)
public class WatchersEye extends AccessoryItem implements Ability
{
    private static final Map<UUID, Integer> cooldownMap = new ConcurrentHashMap<>();

    public WatchersEye()
    {
        super(List.of(
                new StatModifier("maxHealth", 15),
                new StatModifier("armor", 2)
        ));

        setUniqueDes(Component.translatable("item.fatality.watchers_eye.unique"));
    }

    @Override
    public void tick(Player player)
    {
        UUID uuid = player.getUUID();
        Integer cd = cooldownMap.get(uuid);
        if (cd != null && cd > 0)
            cooldownMap.put(uuid, cd - 1);
    }

    @Override
    public boolean onAttacked(Player player, DamageSource source, float amount)
    {
        UUID uuid = player.getUUID();
        if (cooldownMap.getOrDefault(uuid, 0) == 0)
        {
            boolean result = RandomUtil.hitProbability(5);
            if (result)
            {
                cooldownMap.put(uuid, 400);
                ParticleUtil.spawnRingParticles(player.level(), ParticleTypes.END_ROD, player, 32, 1, 0.2);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onUnequipped(Player player)
    {
        cooldownMap.remove(player.getUUID());
    }
}
