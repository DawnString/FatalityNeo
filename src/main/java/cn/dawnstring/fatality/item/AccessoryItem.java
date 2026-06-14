package cn.dawnstring.fatality.item;

import cn.dawnstring.fatality.utils.TooltipHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class AccessoryItem extends Item
{
    private final List<StatModifier> modifiers;

    public AccessoryItem(Properties properties, List<StatModifier> modifiers)
    {
        super(properties.stacksTo(1).durability(0).rarity(Rarity.RARE).setNoRepair());
        this.modifiers = modifiers;
    }

    public List<StatModifier> getModifiers()
    {
        return modifiers;
    }

    public void tick(Player player) {}

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        StringBuilder att = new StringBuilder();
        for (StatModifier m : modifiers)
        {
            String sign = m.value() >= 0 ? "+" : "";
            att.append(" §7").append(m.field()).append(": §e").append(sign).append(m.value());
        }
        TooltipHelper.addDescriptiveTooltip(stack,
                tooltip,
                flag,
                null,
                att.toString());
    }
}
