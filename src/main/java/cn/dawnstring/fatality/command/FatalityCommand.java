package cn.dawnstring.fatality.command;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.core.accessory.AccessoryManager;
import cn.dawnstring.fatality.core.combat.DebugRecorder;
import cn.dawnstring.fatality.core.register.ModCapabilities;
import cn.dawnstring.fatality.item.AccessoryItem;
import cn.dawnstring.fatality.item.StatModifier;
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
                                    source.sendSuccess(() -> Component.literal("=== 玩家属性 ==="), false);
                                    source.sendSuccess(() -> Component.literal("§6[近战] §f基础: §e" + attrs.getMeleeDamageValueBonus() + " §f| 百分比: §e" + attrs.getMeleeDamagePercentBonus() + "% §f| 暴击: §e" + attrs.getMeleeCriticalDamageBonus()), false);
                                    source.sendSuccess(() -> Component.literal("§6[远程] §f基础: §e" + attrs.getRangedDamageValueBonus() + " §f| 百分比: §e" + attrs.getRangedDamagePercentBonus() + "% §f| 暴击: §e" + attrs.getRangedCriticalDamageBonus()), false);
                                    source.sendSuccess(() -> Component.literal("§6[魔法] §f基础: §e" + attrs.getMagicDamageValueBonus() + " §f| 百分比: §e" + attrs.getMagicDamagePercentBonus() + "% §f| 暴击: §e" + attrs.getMagicCriticalDamageBonus()), false);
                                    source.sendSuccess(() -> Component.literal("§6[暴击率] §e" + (attrs.getCriticalHitRate() * 100) + "%"), false);
                                    source.sendSuccess(() -> Component.literal("§6[基础伤害加成] §e" + attrs.getBaseDamagePercentBonus() + "%"), false);
                                    source.sendSuccess(() -> Component.literal("§6[最大生命] §e" + attrs.getMaxHealthBonus()), false);
                                    source.sendSuccess(() -> Component.literal("§6[攻击速度] §e" + attrs.getAttackSpeed()), false);
                                    source.sendSuccess(() -> Component.literal("§6[魔力] §e" + attrs.getCurrentMana() + "§f/§e" + attrs.getMaxMana()), false);
                                    source.sendSuccess(() -> Component.literal("§6[移动速度加成] §e" + attrs.getMoveSpeedBonus()), false);
                                    source.sendSuccess(() -> Component.literal("§6[回复] §f生命: §e" + attrs.getRecoverHealthSpeedBonus() + " §f| 魔力: §e" + attrs.getRecoverManaSpeedBonus()), false);
                                    source.sendSuccess(() -> Component.literal("§6[护甲] §f值: §e" + attrs.getArmor() + " §f| 韧性: §e" + attrs.getArmorToughness()), false);
                                    source.sendSuccess(() -> Component.literal("§6[伤害减免] §e" + (attrs.getDamageReduction() * 100) + "%"), false);
                                    source.sendSuccess(() -> Component.literal("§6[穿透抗性] §f值: §e" + attrs.getPenetrationResistance() + " §f| 系数: §e" + attrs.getPenetrationResistanceCoefficient()), false);
                                }
                                else
                                {
                                    source.sendFailure(Component.literal("属性系统未加载"));
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
                                               source.sendFailure(Component.literal("饰品栏无饰品"));
                                           }
                                       }
                                   }
                               }
                               else
                               {
                                   source.sendFailure(Component.literal("发生错误"));
                               }
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
                                            source.sendSuccess(() -> Component.literal("§a开始记录伤害数据"), false);
                                        }
                                        else
                                        {
                                            var records = DebugRecorder.flush(player.getUUID());
                                            if (records.isEmpty())
                                            {
                                                source.sendSuccess(() -> Component.literal("§e未记录到任何伤害"), false);
                                            }
                                            else
                                            {
                                                source.sendSuccess(() -> Component.literal("§e=== 伤害记录 (" + records.size() + " 条) ==="), false);
                                                for (int idx = 0; idx < records.size(); idx++)
                                                {
                                                    int i = idx;
                                                    int index = i + 1;
                                                    source.sendSuccess(() -> Component.literal("§7" + index + ". " + records.get(i).format()), false);
                                                }
                                            }
                                            source.sendSuccess(() -> Component.literal("§c结束记录"), false);
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
                sb.append("§f").append(mod.field()).append(": §a").append(sign).append(mod.value());
                if (i < modifiers.size() - 1) sb.append("§7, ");
            }
            sb.append("§7]");
        }

        return sb.toString();
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event)
    {
        FatalityCommand.register(event.getDispatcher());
    }
}
