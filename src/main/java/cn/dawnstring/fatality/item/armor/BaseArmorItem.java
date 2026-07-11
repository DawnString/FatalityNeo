package cn.dawnstring.fatality.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;

public class BaseArmorItem extends ArmorItem
{
    private final ArmorStats armorStats;

    public BaseArmorItem(DeferredHolder<ArmorMaterial, ArmorMaterial> material, ArmorItem.Type type, ArmorStats stats, Properties properties)
    {
        super(material, type, properties);
        this.armorStats = stats;
    }

    public ArmorStats getArmorStats()
    {
        return armorStats;
    }
}
