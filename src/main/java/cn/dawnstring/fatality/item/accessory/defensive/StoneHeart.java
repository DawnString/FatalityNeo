package cn.dawnstring.fatality.item.accessory.defensive;

import cn.dawnstring.fatality.core.ability.Ability;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.ItemCategory;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import cn.dawnstring.fatality.register.AutoItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;

import java.util.List;

@AutoItem(itemId = "stone_heart", category = ItemCategory.ACCESSORY)
public class StoneHeart extends AccessoryItem implements Ability
{
    public StoneHeart()
    {
        super(List.of(
                new StatModifier("maxHealth", 30),
                new StatModifier("armor", 3)
        ));
    }

    @Override
    public float onHurt(Player player, DamageSource source, float amount)
    {
        float healthPercent = player.getHealth() / player.getMaxHealth();
        if (healthPercent >= 0.5f)
            return amount;

        // 每少1%生命，减伤增加0.5%
        float extraDR = (0.5f - healthPercent) * 0.5f;
        return amount * (1.0f - extraDR);
    }
}
