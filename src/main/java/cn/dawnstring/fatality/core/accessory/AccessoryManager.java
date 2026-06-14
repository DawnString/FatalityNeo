package cn.dawnstring.fatality.core.accessory;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.core.capability.PlayerAttributes;
import cn.dawnstring.fatality.core.capability.PlayerAttributesProvider;
import cn.dawnstring.fatality.core.register.ModCapabilities;
import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.StatModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AccessoryManager
{
    private static Map<UUID, SimpleContainer> accessoryMap = new HashMap<UUID, SimpleContainer>();
    private static final String KEY = "FatalityAccessories";
    public static final int SLOT_COUNT = 7;

    private static final ResourceLocation MOD_ID_HEALTH = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "accessory_health");
    private static final ResourceLocation MOD_ID_ARMOR = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "accessory_armor");
    private static final ResourceLocation MOD_ID_ARMOR_TOUGHNESS = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "accessory_armor_toughness");
    private static final ResourceLocation MOD_ID_MOVEMENT_SPEED = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "accessory_move_speed");
    private static final ResourceLocation MOD_ID_ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "accessory_attack_speed");

    public static SimpleContainer getInventory(Player player)
    {
        return accessoryMap.computeIfAbsent(player.getUUID(), id ->
        {
            SimpleContainer inv = new SimpleContainer(SLOT_COUNT);
            load(player, inv);
            return inv;
        });
    }

    public static void refreshAttributes(Player player)
    {
        PlayerAttributes attrs = new PlayerAttributes();
        CompoundTag baseTag = player.getPersistentData().getCompound("FatalityPlayerAttributes");
        if (!baseTag.isEmpty())
            attrs.deserializeNBT(RegistryAccess.EMPTY, baseTag);

        SimpleContainer inv = getInventory(player);
        for (int i = 0; i < SLOT_COUNT; i++)
        {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() instanceof AccessoryItem acc)
            {
                for (StatModifier modifier : acc.getModifiers())
                    applyModifier(attrs, modifier);
            }
        }

        PlayerAttributesProvider.updateAttributes(player, attrs);
        syncVanillaAttributes(player, attrs);
    }

    private static void syncVanillaAttributes(Player player, PlayerAttributes attrs)
    {
        float healthBonus = attrs.getMaxHealthBonus() - 20;
        applyVanillaModifier(player, Attributes.MAX_HEALTH, healthBonus, MOD_ID_HEALTH);
        applyVanillaModifier(player, Attributes.ARMOR, attrs.getArmor(), MOD_ID_ARMOR);
        applyVanillaModifier(player, Attributes.ARMOR_TOUGHNESS, attrs.getArmorToughness(), MOD_ID_ARMOR_TOUGHNESS);
        applyVanillaModifier(player, Attributes.MOVEMENT_SPEED, attrs.getMoveSpeedBonus(), MOD_ID_MOVEMENT_SPEED);
        applyVanillaModifier(player, Attributes.ATTACK_SPEED, attrs.getAttackSpeed(), MOD_ID_ATTACK_SPEED);

        if (healthBonus > 0)
        {
            double current = player.getHealth();
            double currentMax = player.getMaxHealth();
            if (current == currentMax - healthBonus)
                player.heal(healthBonus);
            else
                player.setHealth(player.getHealth() + healthBonus);
        }
    }

    private static void applyVanillaModifier(Player player, Holder<Attribute> attribute, double value, ResourceLocation id)
    {
        var instance = player.getAttribute(attribute);
        if (instance == null) return;

        instance.removeModifier(id);

        if (value != 0)
        {
            instance.addTransientModifier(
                    new AttributeModifier(id, value, AttributeModifier.Operation.ADD_VALUE)
            );
        }
    }

    private static void applyModifier(PlayerAttributes playerAttributes, StatModifier modifier)
    {
        switch (modifier.field())
        {
            case "meleeDamageValueBonus" -> playerAttributes.addMeleeDamageValueBonus(modifier.value());
            case "meleeDamagePercentBonus" -> playerAttributes.addMeleeDamagePercentBonus(modifier.value());
            case "meleeCriticalDamageBonus" -> playerAttributes.addMeleeCriticalDamageBonus(modifier.value());
            case "rangedDamageValueBonus" -> playerAttributes.addRangedDamageValueBonus(modifier.value());
            case "rangedDamagePercentBonus" -> playerAttributes.addRangedDamagePercentBonus(modifier.value());
            case "rangedCriticalDamageBonus" -> playerAttributes.addRangedCriticalDamageBonus(modifier.value());
            case "magicDamageValueBonus" -> playerAttributes.addMagicDamageValueBonus(modifier.value());
            case "magicDamagePercentBonus" -> playerAttributes.addMagicDamagePercentBonus(modifier.value());
            case "magicCriticalDamageBonus" -> playerAttributes.addMagicCriticalDamageBonus(modifier.value());
            case "criticalHitRate" -> playerAttributes.addCriticalHitRate(modifier.value());
            case "baseDamagePercentBonus" -> playerAttributes.addBaseDamagePercentBonus(modifier.value());
            case "maxHealth" -> playerAttributes.addMaxHealthBonus((int) modifier.value());
            case "attackSpeed" -> playerAttributes.addAttackSpeed(modifier.value());
            case "mana" -> playerAttributes.addMaxMana((int) modifier.value());
            case "moveSpeedBonus" -> playerAttributes.addMoveSpeedBonus(modifier.value());
            case "recoverHealthSpeedBonus" -> playerAttributes.addRecoverHealthSpeedBonus(modifier.value());
            case "recoverManaSpeedBonus" -> playerAttributes.addRecoverManaSpeedBonus(modifier.value());
            case "armor" -> playerAttributes.addArmor((int) modifier.value());
            case "damageReduction" -> playerAttributes.addDamageReduction(modifier.value());
            case "penetrationResistance" -> playerAttributes.addPenetrationResistance(modifier.value());
            case "penetrationResistanceCoefficient" -> playerAttributes.addPenetrationResistanceCoefficient(modifier.value());
            case "armorToughness" -> playerAttributes.addArmorToughness(modifier.value());
            default -> Fatality.LOGGER.warn("Unknown stat field: {}", modifier.field());
        }
    }

    public static void tick(Player player)
    {
        SimpleContainer inv = getInventory(player);
        for (int i = 0; i < SLOT_COUNT; i++)
        {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof AccessoryItem acc)
                acc.tick(player);
        }
    }

    public static void save(Player player)
    {
        SimpleContainer accessory = accessoryMap.get(player.getUUID());
        if (accessory == null) return;

        ListTag list = new ListTag();
        for (int i = 0; i < SLOT_COUNT; i++)
        {
            CompoundTag tag = new CompoundTag();
            ItemStack stack = accessory.getItem(i);
            if (!stack.isEmpty())
            {
                tag.put("item", stack.save(RegistryAccess.EMPTY, new CompoundTag()));
            }
            list.add(tag);
        }
        player.getPersistentData().put(KEY, list);
    }

    public static void load(Player player, SimpleContainer accessory)
    {
        ListTag list = player.getPersistentData().getList(KEY, ListTag.TAG_COMPOUND);
        for (int i = 0; i < Math.min(list.size(), SLOT_COUNT); i++)
        {
            CompoundTag tag = list.getCompound(i);
            if (tag.contains("item"))
            {
                int finalI = i;
                ItemStack.parse(RegistryAccess.EMPTY, tag.getCompound("item"))
                        .ifPresent(itemStack -> accessory.setItem(finalI, itemStack));
            }
        }
    }

    public static void remove(UUID uuid)
    {
        accessoryMap.remove(uuid);
    }
}
