package cn.dawnstring.fatality.command;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.core.accessory.AccessoryManager;
import cn.dawnstring.fatality.core.combat.DebugRecorder;
import cn.dawnstring.fatality.core.register.ModCapabilities;
import cn.dawnstring.fatality.item.accessory.AccessoryItem;
import cn.dawnstring.fatality.item.accessory.StatModifier;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.List;

@EventBusSubscriber(modid = Fatality.MODID)
public class FatalityCommand
{
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
        dispatcher.register(LiteralArgumentBuilder.<CommandSourceStack>literal("fatality")
                .then(LiteralArgumentBuilder.<CommandSourceStack>literal("attribute")
                        .executes(context ->
                        {
                            CommandSourceStack source = context.getSource();
                            if (source.getEntity() instanceof ServerPlayer player)
                            {
                                var attrs = player.getCapability(ModCapabilities.PLAYER_ATTRIBUTES);
                                if (attrs != null)
                                {
                                    var melee = tr("command.fatality.melee");
                                    var ranged = tr("command.fatality.ranged");
                                    var magic = tr("command.fatality.magic");
                                    var base = tr("command.fatality.base");
                                    var pct = tr("command.fatality.percent");
                                    var crit = tr("command.fatality.crit");
                                    var critRate = tr("command.fatality.crit_rate");
                                    var baseDmg = tr("command.fatality.base_damage_bonus");
                                    var maxHp = tr("command.fatality.max_health");
                                    var atkSpeed = tr("command.fatality.attack_speed");
                                    var manaDisp = tr("command.fatality.mana_display");
                                    var moveSpd = tr("command.fatality.move_speed");
                                    var recov = tr("command.fatality.recovery");
                                    var hp = tr("command.fatality.health");
                                    var mana = tr("command.fatality.mana");
                                    var arm = tr("command.fatality.armor");
                                    var tough = tr("command.fatality.toughness");
                                    var val = tr("command.fatality.value");
                                    var dmgRed = tr("command.fatality.damage_reduction");
                                    var penRes = tr("command.fatality.penetration_resist");
                                    var penCoeff = tr("command.fatality.penetration_coeff");

                                    source.sendSuccess(() -> Component.literal("§6" + tr("command.fatality.attribute_title")), false);
                                    source.sendSuccess(() -> Component.literal(String.format("§6[%s] §f%s: §e%s §f| %s: §e%s%% §f| %s: §e%s",
                                            melee, base, fmt(attrs.getMeleeDamageValueBonus()), pct, attrs.getMeleeDamagePercentBonus(), crit, fmt(attrs.getMeleeCriticalDamageBonus()))), false);
                                    source.sendSuccess(() -> Component.literal(String.format("§6[%s] §f%s: §e%s §f| %s: §e%s%% §f| %s: §e%s",
                                            ranged, base, fmt(attrs.getRangedDamageValueBonus()), pct, attrs.getRangedDamagePercentBonus(), crit, fmt(attrs.getRangedCriticalDamageBonus()))), false);
                                    source.sendSuccess(() -> Component.literal(String.format("§6[%s] §f%s: §e%s §f| %s: §e%s%% §f| %s: §e%s",
                                            magic, base, fmt(attrs.getMagicDamageValueBonus()), pct, attrs.getMagicDamagePercentBonus(), crit, fmt(attrs.getMagicCriticalDamageBonus()))), false);
                                    source.sendSuccess(() -> Component.literal("§6[" + critRate + "] §e" + String.format("%.0f", attrs.getCriticalHitRate() * 100) + "%"), false);
                                    source.sendSuccess(() -> Component.literal("§6[" + baseDmg + "] §e" + fmt(attrs.getBaseDamagePercentBonus()) + "%"), false);
                                    source.sendSuccess(() -> Component.literal("§6[" + maxHp + "] §e" + attrs.getMaxHealthBonus()), false);
                                    source.sendSuccess(() -> Component.literal("§6[" + atkSpeed + "] §e" + fmt(attrs.getAttackSpeed())), false);
                                    source.sendSuccess(() -> Component.literal("§6[" + manaDisp + "] §e" + attrs.getCurrentMana() + "§f/§e" + attrs.getMaxMana()), false);
                                    source.sendSuccess(() -> Component.literal("§6[" + moveSpd + "] §e" + fmt(attrs.getMoveSpeedBonus())), false);
                                    source.sendSuccess(() -> Component.literal(String.format("§6[%s] §f%s: §e%s §f| %s: §e%s",
                                            recov, hp, fmt(attrs.getRecoverHealthSpeedBonus()), mana, fmt(attrs.getRecoverManaSpeedBonus()))), false);
                                    source.sendSuccess(() -> Component.literal(String.format("§6[%s] §f%s: §e%s §f| %s: §e%s",
                                            arm, val, attrs.getArmor(), tough, fmt(attrs.getArmorToughness()))), false);
                                    source.sendSuccess(() -> Component.literal("§6[" + dmgRed + "] §e" + String.format("%.0f", attrs.getDamageReduction() * 100) + "%"), false);
                                    source.sendSuccess(() -> Component.literal(String.format("§6[%s] §f%s: §e%s §f| %s: §e%s",
                                            penRes, val, fmt(attrs.getPenetrationResistance()), penCoeff, fmt(attrs.getPenetrationResistanceCoefficient()))), false);
                                }
                                else
                                {
                                    source.sendFailure(Component.translatable("command.fatality.system_not_loaded"));
                                }
                            }
                            return 1;
                        }))
                .then(LiteralArgumentBuilder.<CommandSourceStack>literal("accessory")
                        .executes(context ->
                        {
                            CommandSourceStack source = context.getSource();
                            if (source.getEntity() instanceof ServerPlayer player)
                            {
                               SimpleContainer accessories = AccessoryManager.getInventory(player);
                               if (accessories.getItems() != null)
                               {
                                   for (ItemStack item : accessories.getItems())
                                   {
                                       if (item.isEmpty()) continue;
                                       if (item.getItem() instanceof AccessoryItem accessoryItem)
                                       {
                                           String info = getAccessoryInfo(accessoryItem);
                                           if (info != null)
                                           {
                                               source.sendSuccess(() -> Component.literal(info), false);
                                           }
                                           else
                                           {
                                               source.sendFailure(Component.translatable("command.fatality.error"));
                                           }
                                       }
                                   }
                               }
                               else
                               {
                                   source.sendFailure(Component.translatable("command.fatality.error"));
                               }
                            }
                            else
                            {
                                source.sendFailure(Component.translatable("command.fatality.no_accessory"));
                            }
                            return 1;
                        }))
                .then(LiteralArgumentBuilder.<CommandSourceStack>literal("debug")
                        .then(LiteralArgumentBuilder.<CommandSourceStack>literal("damageCalc")
                                .executes(context ->
                                {
                                    CommandSourceStack source = context.getSource();
                                    if (source.getEntity() instanceof ServerPlayer player)
                                    {
                                        boolean nowActive = DebugRecorder.toggle(player.getUUID());
                                        if (nowActive)
                                        {
                                            source.sendSuccess(() -> Component.translatable("command.fatality.debug_start").withStyle(style -> style.withColor(net.minecraft.ChatFormatting.GREEN)), false);
                                        }
                                        else
                                        {
                                            var records = DebugRecorder.flush(player.getUUID());
                                            if (records.isEmpty())
                                            {
                                                source.sendSuccess(() -> Component.translatable("command.fatality.debug_no_data").withStyle(style -> style.withColor(net.minecraft.ChatFormatting.YELLOW)), false);
                                            }
                                            else
                                            {
                                                String title = String.format(Component.translatable("command.fatality.debug_title").getString(), records.size());
                                                source.sendSuccess(() -> Component.literal("§e" + title), false);
                                                for (int idx = 0; idx < records.size(); idx++)
                                                {
                                                    int i = idx;
                                                    int index = i + 1;
                                                    source.sendSuccess(() -> Component.literal("§7" + index + ". " + records.get(i).format()), false);
                                                }
                                            }
                                            source.sendSuccess(() -> Component.translatable("command.fatality.debug_end").withStyle(style -> style.withColor(net.minecraft.ChatFormatting.RED)), false);
                                        }
                                    }
                                    return 1;
                                })
                        )
                )
        );
    }

    //饰品信息获取
    private static String getAccessoryInfo(AccessoryItem accessoryItem)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("§e").append(Component.translatable(accessoryItem.getDescriptionId()).getString());

        List<StatModifier> modifiers = accessoryItem.getModifiers();
        if (!modifiers.isEmpty())
        {
            sb.append(" §7[");
            for (int i = 0; i < modifiers.size(); i++)
            {
                var mod = modifiers.get(i);
                String sign = mod.value() >= 0 ? "+" : "";
                String fieldName = Component.translatable("stat.fatality." + mod.field()).getString();
                sb.append("§f").append(fieldName).append(": §a").append(sign).append(fmt(mod.value()));
                if (i < modifiers.size() - 1) sb.append("§7, ");
            }
            sb.append("§7]");
        }

        return sb.toString();
    }

    private static String fmt(float v)
    {
        return v == (int) v ? String.valueOf((int) v) : String.format("%.1f", v);
    }

    private static String tr(String key)
    {
        return Component.translatable(key).getString();
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event)
    {
        FatalityCommand.register(event.getDispatcher());
    }
}
