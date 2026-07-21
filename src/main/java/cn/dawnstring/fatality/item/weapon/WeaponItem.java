package cn.dawnstring.fatality.item.weapon;

import cn.dawnstring.fatality.item.UniqueItemType;
import cn.dawnstring.fatality.item.weapon.projectile.ProjectileBehavior;
import cn.dawnstring.fatality.item.weapon.projectile.ProjectileStats;
import cn.dawnstring.fatality.utils.TooltipUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nullable;
import java.util.List;

public abstract class WeaponItem extends Item
{
    private final WeaponStats stats;
    private final List<WeaponPassive> passives;
    private final WeaponActive activeAbility;
    private Component story;
    private Component uniqueDes;
    private Component defaultDes;
    private Component uniqueItemTypeDes;

    public WeaponItem(Properties properties, WeaponStats stats, List<WeaponPassive> passives, WeaponActive activeAbility)
    {
        super(properties);
        this.stats = stats;
        this.passives = passives;
        this.activeAbility = activeAbility;
    }

    public WeaponItem(Properties properties, WeaponStats stats, List<WeaponPassive> passives)
    {
        this(properties, stats, passives, null);
    }

    public WeaponStats getWeaponStats()
    {
        return stats;
    }

    public List<WeaponPassive> getPassives()
    {
        return passives;
    }

    public WeaponActive getActiveAbility()
    {
        return activeAbility;
    }

    public boolean hasActiveAbility()
    {
        return activeAbility != null;
    }

    /**
     * 弹幕武器覆盖此方法，返回该武器发射的弹幕属性
     */
    @Nullable
    public ProjectileStats getProjectileStats() { return null; }

    /**
     * 弹幕武器覆盖此方法，返回该武器发射的弹幕行为组件
     */
    public List<ProjectileBehavior> getProjectileBehaviors() { return List.of(); }

    public void setStory(Component story)
    {
        this.story = story;
    }

    public void setUniqueDes(Component uniqueDes)
    {
        this.uniqueDes = uniqueDes;
    }

    public void setDefaultDes(Component defaultDes)
    {
        this.defaultDes = defaultDes;
    }

    public void setUniqueItemTypeDes(UniqueItemType uniqueItemTypeDes)
    {
        switch (uniqueItemTypeDes)
        {
            case SUPPORTER_ITEM:
                this.uniqueItemTypeDes = Component.translatable("des.fatality.supporter_item");
            case DEVELOPER_ITEM:
                this.uniqueItemTypeDes = Component.translatable("des.fatality.developer_item");
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag)
    {
        String typeName = Component.translatable("weapon.fatality.type." + stats.weaponType().name().toLowerCase()).getString();

        StringBuilder sb = new StringBuilder();
        sb.append("§7").append(Component.translatable("weapon.fatality.type").getString()).append(": §e").append(typeName).append("\n");
        sb.append("§7").append(Component.translatable("weapon.fatality.base_damage").getString()).append(": §e").append(String.format("%.1f", stats.baseDamage())).append("\n");
        sb.append("§7").append(Component.translatable("weapon.fatality.crit_rate").getString()).append(": §e").append(String.format("%.1f", stats.critRate() * 100)).append("%\n");
        sb.append("§7").append(Component.translatable("weapon.fatality.crit_damage").getString()).append(": §e+").append(String.format("%.0f", stats.critDamage() * 100)).append("%\n");
        sb.append("§7").append(Component.translatable("weapon.fatality.fluctuation").getString()).append(": §e").append(String.format("%.2f", stats.fluctuation())).append("\n");
        sb.append("§7").append(Component.translatable("weapon.fatality.attack_speed").getString()).append(": §e").append(String.format("%.1f", stats.attSpeed())).append("\n");

        if (uniqueDes != null)
            sb.append(uniqueDes.getString()).append("\n");

        if (story != null)
            TooltipUtil.addDescriptiveTooltip(stack, tooltip, flag, story.getString(), sb.toString(), false);
        else
            TooltipUtil.addDescriptiveTooltip(stack, tooltip, flag, null, sb.toString(), false);

        if (defaultDes != null)
            TooltipUtil.addDefaultTooltip(tooltip, defaultDes.getString(), false);

        if (uniqueItemTypeDes != null)
            TooltipUtil.addDefaultTooltip(tooltip, uniqueItemTypeDes.getString(), true);
    }
}
