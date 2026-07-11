package cn.dawnstring.fatality.client.gui.hud;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.core.accessory.AccessoryManager;
import cn.dawnstring.fatality.core.combat.RegenSystem;
import cn.dawnstring.fatality.item.BaseWingItem;
import cn.dawnstring.fatality.item.WingStats;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FatalityHudOverlay implements LayeredDraw.Layer
{
    public static final FatalityHudOverlay INSTANCE = new FatalityHudOverlay();

    private static final ResourceLocation HEALTH_BAR = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "textures/gui/health_bar.png");
    private static final ResourceLocation FOOD_BAR   = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "textures/gui/food_bar.png");
    private static final ResourceLocation MANA_BAR   = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "textures/gui/mana_bar.png");
    private static final ResourceLocation DEC_BAR    = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "textures/gui/universal_dec_bar.png");
    private static final ResourceLocation FLIGHT_BAR     = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "textures/gui/flight_time_bar.png");
    private static final ResourceLocation FLIGHT_DEC_BAR = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "textures/gui/flight_time_dec_bar.png");

    private static final ResourceLocation ARMOR_SPRITE = ResourceLocation.withDefaultNamespace("hud/armor_full");

    // === 贴图原始像素尺寸 ===
    private static final int TEX_DEC_W = 146;
    private static final int TEX_DEC_H = 28;
    private static final int TEX_BAR_W = 140;
    private static final int TEX_BAR_H = 11;
    private static final int TEX_FLIGHT_DEC_W = 10;
    private static final int TEX_FLIGHT_DEC_H = 59;
    private static final int TEX_FLIGHT_FILL_W = 6;
    private static final int TEX_FLIGHT_FILL_H = 55;

    // === 填充贴图在外框中的偏移（外框左上为 0,0） ===
    private static final int FILL_OX = 3;
    private static final int FILL_OY = 14;
    private static final int FLIGHT_FILL_OX = 2;
    private static final int FLIGHT_FILL_OY = 2;

    // === 缩放系数（1.0 = 原始像素大小） ===
    private static final float BAR_SCALE = 0.55f;
    private static final float TEXT_SCALE = 0.72f;

    // === 全局 Y 偏移（正数=上移，避免挡住经验值） ===
    private static final int Y_OFFSET = 12;

    // === 屏幕位置（相对于屏幕中心/底部，已考虑缩放） ===
    private static final int HEALTH_X = -91;
    private static final int HEALTH_Y = -39 - Y_OFFSET;
    private static final int MANA_X   = -91;
    private static final int MANA_Y   = -39 - (int) (TEX_DEC_H * BAR_SCALE) - 2 - Y_OFFSET;
    private static final int ARMOR_X  = 0;
    private static final int ARMOR_Y  = -49 - Y_OFFSET;

    // 饱食度位置：右对齐到热键栏右边缘（centerX + 91）
    private static final int FOOD_Y   = -39 - Y_OFFSET;

    private int leftHeight;
    private int rightHeight;

    @Override
    public void render(GuiGraphics gui, DeltaTracker delta)
    {
        Player player = Minecraft.getInstance().player;

        if (player == null || player.isCreative() || player.isSpectator())
            return;

        leftHeight = 0;
        rightHeight = 0;

        int w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        int h = Minecraft.getInstance().getWindow().getGuiScaledHeight();
        int cx = w / 2;
        int by = h;

        renderHealthBar(gui, player, cx, by);
        renderFoodBar(gui, player, cx, by);
        renderManaBar(gui, player, cx, by);
        renderArmorDisplay(gui, player, cx, by);
        renderFlightBar(gui, cx, by);
    }

    private void renderHealthBar(GuiGraphics gui, Player player, int cx, int by)
    {
        int x = cx + HEALTH_X;
        int y = by + HEALTH_Y;

        var pose = gui.pose();
        pose.pushPose();
        pose.translate(x, y, 0);
        pose.scale(BAR_SCALE, BAR_SCALE, 1.0f);

        gui.blit(DEC_BAR, 0, 0, 0, 0, TEX_DEC_W, TEX_DEC_H, TEX_DEC_W, TEX_DEC_H);

        float pct = Math.max(0, player.getHealth() / player.getMaxHealth());
        int fill = Math.round(TEX_BAR_W * pct);
        if (fill > 0)
            gui.blit(HEALTH_BAR, FILL_OX, FILL_OY, 0, 0, fill, TEX_BAR_H, TEX_BAR_W, TEX_BAR_H);

        pose.popPose();
        leftHeight += (int) (TEX_DEC_H * BAR_SCALE);

        // 血量数值（缩放，放在左侧）
        Component text = Component.literal(String.valueOf((int) player.getHealth()));
        int textWidth = Minecraft.getInstance().font.width(text);
        int textX = x - (int) (textWidth * TEXT_SCALE) - 3;
        int textY = y + (int) ((FILL_OY + (TEX_BAR_H - 9) / 2f) * BAR_SCALE);

        var textPose = gui.pose();
        textPose.pushPose();
        textPose.translate(textX, textY, 0);
        textPose.scale(TEXT_SCALE, TEXT_SCALE, 1.0f);
        gui.drawString(Minecraft.getInstance().font, text, 0, 0, 0xFFFFFF);
        textPose.popPose();
    }

    private void renderFoodBar(GuiGraphics gui, Player player, int cx, int by)
    {
        int barScreenW = (int) (TEX_DEC_W * BAR_SCALE);
        int x = cx + 91 - barScreenW;
        int y = by + FOOD_Y;

        var pose = gui.pose();
        pose.pushPose();
        pose.translate(x, y, 0);
        pose.scale(BAR_SCALE, BAR_SCALE, 1.0f);

        gui.blit(DEC_BAR, 0, 0, 0, 0, TEX_DEC_W, TEX_DEC_H, TEX_DEC_W, TEX_DEC_H);

        float pct = Math.max(0, player.getFoodData().getFoodLevel() / 20f);
        int fill = Math.round(TEX_BAR_W * pct);
        if (fill > 0)
            gui.blit(FOOD_BAR, FILL_OX, FILL_OY, 0, 0, fill, TEX_BAR_H, TEX_BAR_W, TEX_BAR_H);

        pose.popPose();
        rightHeight += (int) (TEX_DEC_H * BAR_SCALE);
    }

    private void renderManaBar(GuiGraphics gui, Player player, int cx, int by)
    {
        int x = cx + MANA_X;
        int y = by + MANA_Y;

        var pose = gui.pose();
        pose.pushPose();
        pose.translate(x, y, 0);
        pose.scale(BAR_SCALE, BAR_SCALE, 1.0f);

        gui.blit(DEC_BAR, 0, 0, 0, 0, TEX_DEC_W, TEX_DEC_H, TEX_DEC_W, TEX_DEC_H);

        int mana = RegenSystem.getMana(player);
        int maxMana = RegenSystem.getMaxMana(player);
        float pct = maxMana > 0 ? Math.max(0, mana / (float) maxMana) : 0;
        int fill = Math.round(TEX_BAR_W * pct);
        if (fill > 0)
            gui.blit(MANA_BAR, FILL_OX, FILL_OY, 0, 0, fill, TEX_BAR_H, TEX_BAR_W, TEX_BAR_H);

        pose.popPose();
        leftHeight += (int) (TEX_DEC_H * BAR_SCALE);

        // 魔法数值（缩放，放在左侧）
        Component text = Component.literal(String.valueOf(mana));
        int textWidth = Minecraft.getInstance().font.width(text);
        int textX = x - (int) (textWidth * TEXT_SCALE) - 3;
        int textY = y + (int) ((FILL_OY + (TEX_BAR_H - 9) / 2f) * BAR_SCALE);

        var textPose = gui.pose();
        textPose.pushPose();
        textPose.translate(textX, textY, 0);
        textPose.scale(TEXT_SCALE, TEXT_SCALE, 1.0f);
        gui.drawString(Minecraft.getInstance().font, text, 0, 0, 0xFFFFFF);
        textPose.popPose();
    }

    private void renderArmorDisplay(GuiGraphics gui, Player player, int cx, int by)
    {
        int armor = player.getArmorValue();
        if (armor <= 0) return;

        int y = by + ARMOR_Y;

        // 居中图标+文字组
        Component text = Component.literal("§f" + armor);
        int textWidth = Minecraft.getInstance().font.width(text);
        int scaledTextW = (int) (textWidth * TEXT_SCALE);
        int totalW = 9 + 1 + scaledTextW;
        int groupX = cx + ARMOR_X - totalW / 2;

        gui.blitSprite(ARMOR_SPRITE, groupX, y, 9, 9);

        var pose = gui.pose();
        pose.pushPose();
        pose.translate(groupX + 10, y, 0);
        pose.scale(TEXT_SCALE, TEXT_SCALE, 1.0f);
        gui.drawString(Minecraft.getInstance().font, text, 0, 0, 0xFFFFFF);
        pose.popPose();
    }

    public void renderFlightBar(GuiGraphics gui, int cx, int by)
    {
        Player player = Minecraft.getInstance().player;
        if (player == null)
            return;

        var inv = AccessoryManager.getInventory(player);
        WingStats stats = null;
        for (int i = 0; i < AccessoryManager.SLOT_COUNT; i++)
        {
            ItemStack stack = inv.getItem(i);
            if (stack.getItem() instanceof BaseWingItem wing)
            {
                stats = wing.getWingStats();
                break;
            }
        }
        if (stats == null)
            return;

        int remaining = BaseWingItem.getRemainingFlightTime(player);
        int maxTime = stats.maxFlightTime();
        if (maxTime <= 0) return;

        float pct = Math.max(0, remaining / (float) maxTime);

        // 位置：热键栏右侧
        int barScreenW = (int) (TEX_FLIGHT_DEC_W * BAR_SCALE);
        int barScreenH = (int) (TEX_FLIGHT_DEC_H * BAR_SCALE);
        int barX = cx + 91 + 4;
        int barY = by - 22 - barScreenH;

        var pose = gui.pose();
        pose.pushPose();
        pose.translate(barX, barY, 0);
        pose.scale(BAR_SCALE, BAR_SCALE, 1.0f);

        // 外框
        gui.blit(FLIGHT_DEC_BAR, 0, 0, 0, 0, TEX_FLIGHT_DEC_W, TEX_FLIGHT_DEC_H, TEX_FLIGHT_DEC_W, TEX_FLIGHT_DEC_H);

        // 飞行时间填充（从下往上）
        int fill = Math.round(TEX_FLIGHT_FILL_H * pct);
        if (fill > 0)
            gui.blit(FLIGHT_BAR,
                    FLIGHT_FILL_OX, FLIGHT_FILL_OY + TEX_FLIGHT_FILL_H - fill,
                    0, TEX_FLIGHT_FILL_H - fill,
                    TEX_FLIGHT_FILL_W, fill,
                    TEX_FLIGHT_FILL_W, TEX_FLIGHT_FILL_H);

        pose.popPose();
    }
}
