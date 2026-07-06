package cn.dawnstring.fatality.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class TooltipHelper
{
    private static boolean isAltDown()
    {
        long window = Minecraft.getInstance().getWindow().getWindow();
        return GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS ||
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_ALT) == GLFW.GLFW_PRESS;
    }

    private static boolean isShiftDown()
    {
        long window = Minecraft.getInstance().getWindow().getWindow();
        return GLFW.glfwGetKey(window, GLFW.GLFW_KEY_LEFT_SHIFT) == GLFW.GLFW_PRESS ||
                GLFW.glfwGetKey(window, GLFW.GLFW_KEY_RIGHT_SHIFT) == GLFW.GLFW_PRESS;
    }

    /**
     * 添加物品描述工具提示
     * @param stack 物品堆栈
     * @param tooltip 工具提示列表
     * @param flag 工具提示标志
     * @param story 物品故事
     * @param attributes 物品属性描述
     */
    public static void addDescriptiveTooltip(ItemStack stack, List<Component> tooltip,
                                             TooltipFlag flag, String story, String attributes)
    {
        // 检查是否按住Shift键
        boolean isShiftDown = isShiftDown();
        // 检查是否按住Alt键
        boolean isAltDown = isAltDown();

        // 默认显示提示信息
        if (!isShiftDown && !isAltDown)
        {
            String altKey = Component.translatable("key.fatality.alt").getString();
            String shiftKey = Component.translatable("key.fatality.shift").getString();
            tooltip.add(Component.literal("§7" + Component.translatable("tooltip.fatality.hold_alt", altKey).getString()));
            tooltip.add(Component.literal("§7" + Component.translatable("tooltip.fatality.hold_shift", shiftKey).getString()));
            return;
        }

        // 按住Alt显示物品故事
        if (isAltDown)
        {
            if (story != null && !story.isEmpty())
            {
                tooltip.add(Component.literal("§6" + Component.translatable("tooltip.fatality.section_desc").getString()));
                String[] storyLines = story.split("\n");
                for (String line : storyLines)
                {
                    tooltip.add(Component.literal("§e" + line));
                }
            }
            else
            {
                tooltip.add(Component.literal("§7" + Component.translatable("tooltip.fatality.no_desc").getString()));
            }
        }

        // 按住Shift显示物品属性
        if (isShiftDown)
        {
            if (attributes != null && !attributes.isEmpty())
            {
                tooltip.add(Component.literal("§6" + Component.translatable("tooltip.fatality.section_attr").getString()));
                String[] attrLines = attributes.split("\n");
                for (String line : attrLines)
                {
                    tooltip.add(Component.literal("§e" + line));
                }
            }
            else
            {
                tooltip.add(Component.literal("§7" + Component.translatable("tooltip.fatality.no_attr").getString()));
            }
        }
    }
}
