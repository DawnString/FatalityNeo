package cn.dawnstring.fatality.guide;

import cn.dawnstring.fatality.guide.data.GuideCategory;
import cn.dawnstring.fatality.guide.data.GuideEntry;
import cn.dawnstring.fatality.guide.data.GuidePage;
import cn.dawnstring.fatality.guide.loader.GuideLoader;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class FinalWordsScreen extends Screen
{
    private static final float BOOK_WIDTH_RATIO = 0.78f;
    private static final float BOOK_HEIGHT_RATIO = 0.88f;
    private static final float LEFT_RATIO = 0.26f;

    private static final int COLOR_COVER_BG = 0xFF1A0E00;
    private static final int COLOR_COVER_EDGE = 0xFF0D0700;
    private static final int COLOR_PAGE = 0xFF1E1E1E;
    private static final int COLOR_PAGE_EDGE = 0xFF3A3A3A;
    private static final int COLOR_TEXT = 0xFFD0D0D0;
    private static final int COLOR_TITLE = 0xFFFFD700;
    private static final int COLOR_CAT = 0xFFD4A050;
    private static final int COLOR_CAT_HOVER = 0xFFE0B060;
    private static final int COLOR_ENTRY_TEXT = 0xFFB0B0B0;
    private static final int COLOR_ENTRY_SEL = 0xFF4A3520;
    private static final int COLOR_ENTRY_HOVER = 0xFF3A2815;
    private static final int COLOR_BTN_BG = 0xFF5C4A20;
    private static final int COLOR_BTN_TEXT = 0xFFE0D0B0;
    private static final int COLOR_DIVIDER = 0xFF4A3A20;
    private static final int COLOR_ITEM_BG = 0xFF2A2A2A;
    private static final int COLOR_ITEM_BORDER = 0xFF5C4A20;

    private int bookLeft, bookTop, bookWidth, bookHeight;
    private int leftWidth, rightLeft, rightWidth;

    private GuideCategory selectedCategory;
    private GuideEntry selectedEntry;
    private int currentPage;

    private GuideCategory expandedCategory;
    private int leftScrollOffset;
    private int leftContentHeight;

    private final List<TreeItem> treeItems = new ArrayList<>();
    private ItemStack hoveredItemStack = ItemStack.EMPTY;

    private record TreeItem(GuideCategory category, GuideEntry entry, int indent) {}

    public FinalWordsScreen()
    {
        super(Component.translatable("gui.fatality.final_words"));
    }

    @Override
    protected void init()
    {
        bookWidth  = (int) (width * BOOK_WIDTH_RATIO);
        bookHeight = (int) (height * BOOK_HEIGHT_RATIO);
        bookLeft   = (width - bookWidth) / 2;
        bookTop    = (height - bookHeight) / 2;
        leftWidth  = (int) (bookWidth * LEFT_RATIO);
        rightLeft  = bookLeft + leftWidth + 2;
        rightWidth = bookWidth - leftWidth - 4;

        // 确保引导数据已加载
        if (GuideLoader.getCategories().isEmpty())
            GuideLoader.load(minecraft.getResourceManager());

        buildTree();
        selectFirstWithEntry();
    }

    private void buildTree()
    {
        treeItems.clear();
        var allCats = GuideLoader.getCategories();
        if (allCats.isEmpty()) return;

        var rootCats = allCats.values().stream()
                .filter(c -> c.parentId() == null)
                .sorted(Comparator.comparingInt(GuideCategory::sortnum))
                .toList();

        for (var cat : rootCats)
        {
            treeItems.add(new TreeItem(cat, null, 0));

            var isExpanded = cat.equals(expandedCategory)
                    || (selectedCategory != null && cat.id().equals(selectedCategory.parentId()));

            if (isExpanded)
            {
                var subCats = allCats.values().stream()
                        .filter(c -> cat.id().equals(c.parentId()))
                        .sorted(Comparator.comparingInt(GuideCategory::sortnum))
                        .toList();

                boolean expandSub = cat.equals(expandedCategory);

                for (var sub : subCats)
                {
                    treeItems.add(new TreeItem(sub, null, 1));

                    if (expandSub)
                    {
                        var entryMap = GuideLoader.getEntries();
                        for (var eid : sub.entries())
                        {
                            var entry = entryMap.get(eid);
                            if (entry != null && sub.id().equals(entry.category()))
                                treeItems.add(new TreeItem(null, entry, 2));
                        }
                    }
                }

                if (expandSub)
                {
                    var entryMap = GuideLoader.getEntries();
                    for (var eid : cat.entries())
                    {
                        var entry = entryMap.get(eid);
                        if (entry != null)
                        {
                            var inSub = subCats.stream().anyMatch(s -> s.id().equals(entry.category()));
                            if (!inSub)
                                treeItems.add(new TreeItem(null, entry, 1));
                        }
                    }
                }
            }
        }

        var lineCount = treeItems.size();
        leftContentHeight = lineCount * 13 + 10;
    }

    private void selectFirstWithEntry()
    {
        for (var item : treeItems)
        {
            if (item.entry() != null)
            {
                selectEntry(item.category(), item.entry());
                return;
            }
        }
        if (!treeItems.isEmpty() && treeItems.get(0).category() != null)
            selectCategory(treeItems.get(0).category());
    }

    private void selectCategory(GuideCategory cat)
    {
        expandedCategory = (expandedCategory != null && expandedCategory.equals(cat)) ? null : cat;
        selectedCategory = cat;
        currentPage = 0;
        leftScrollOffset = 0;
        buildTree();
        selectFirstEntryIn(cat);
    }

    private void selectFirstEntryIn(GuideCategory cat)
    {
        var entries = GuideLoader.getEntries();
        for (var eid : cat.entries())
        {
            var entry = entries.get(eid);
            if (entry != null)
            {
                selectedEntry = entry;
                return;
            }
        }
    }

    private void selectEntry(GuideCategory cat, GuideEntry entry)
    {
        selectedCategory = cat;
        selectedEntry = entry;
        currentPage = 0;
    }

    @Override
    public void render(GuiGraphics g, int mx, int my, float pt)
    {
        hoveredItemStack = ItemStack.EMPTY;
        renderBackground(g, mx, my, pt);

        g.fill(bookLeft + 4, bookTop + 4, bookLeft + bookWidth + 4, bookTop + bookHeight + 4, 0x40000000);

        g.fill(bookLeft, bookTop, bookLeft + leftWidth, bookTop + bookHeight, COLOR_COVER_BG);
        g.fill(bookLeft, bookTop, bookLeft + leftWidth, bookTop + 2, COLOR_COVER_EDGE);
        g.fill(bookLeft, bookTop + bookHeight - 2, bookLeft + leftWidth, bookTop + bookHeight, COLOR_COVER_EDGE);
        g.fill(bookLeft, bookTop, bookLeft + 2, bookTop + bookHeight, COLOR_COVER_EDGE);
        g.fill(bookLeft + leftWidth - 2, bookTop, bookLeft + leftWidth, bookTop + bookHeight, COLOR_COVER_EDGE);

        var coverTitle = Component.translatable("gui.fatality.final_words");
        g.drawString(font, coverTitle, bookLeft + (leftWidth - font.width(coverTitle)) / 2,
                bookTop + 6, COLOR_CAT, false);

        g.fill(rightLeft, bookTop, rightLeft + rightWidth, bookTop + bookHeight, COLOR_PAGE);
        g.fill(rightLeft, bookTop, rightLeft + rightWidth, bookTop + 2, COLOR_PAGE_EDGE);
        g.fill(rightLeft, bookTop + bookHeight - 2, rightLeft + rightWidth, bookTop + bookHeight, COLOR_PAGE_EDGE);
        g.fill(rightLeft, bookTop, rightLeft + 2, bookTop + bookHeight, COLOR_PAGE_EDGE);
        g.fill(rightLeft + rightWidth - 2, bookTop, rightLeft + rightWidth, bookTop + bookHeight, COLOR_PAGE_EDGE);

        g.fill(bookLeft + leftWidth, bookTop, bookLeft + leftWidth + 2, bookTop + bookHeight, COLOR_DIVIDER);

        renderTree(g, mx, my);

        if (selectedEntry != null && !selectedEntry.pages().isEmpty())
        {
            if (currentPage >= selectedEntry.pages().size())
                currentPage = selectedEntry.pages().size() - 1;

            renderPage(g, selectedEntry.pages().get(currentPage), mx, my);
            renderPageNav(g, mx, my);
        }
        else
        {
            var noContent = Component.translatable("gui.fatality.guide.no_content");
            g.drawString(font, noContent,
                    rightLeft + (rightWidth - font.width(noContent)) / 2,
                    bookTop + bookHeight / 2 - 4, COLOR_TEXT, false);
        }

        if (!hoveredItemStack.isEmpty())
            g.renderTooltip(font, hoveredItemStack, mx, my);
    }

    private void renderTree(GuiGraphics g, int mx, int my)
    {
        g.enableScissor(bookLeft + 3, bookTop + 20, bookLeft + leftWidth - 3, bookTop + bookHeight - 3);

        if (treeItems.isEmpty())
        {
            var msg = Component.translatable("gui.fatality.guide.empty");
            g.drawString(font, msg, bookLeft + 6, bookTop + 24, COLOR_CAT, false);
            g.disableScissor();
            return;
        }

        int x = bookLeft + 6;
        int baseY = bookTop + 20 - leftScrollOffset;

        for (var item : treeItems)
        {
            int ix = x + item.indent() * 10;
            int iy = baseY;
            int lineH = 13;

            if (iy + lineH > bookTop + 20 && iy < bookTop + bookHeight - 3)
            {
                boolean hovered = mx >= ix && mx <= bookLeft + leftWidth - 4 && my >= iy && my < iy + lineH;

                if (item.category() != null)
                {
                    boolean isExpanded = item.category().equals(expandedCategory);
                    boolean isSelected = item.category().equals(selectedCategory);

                    if (isSelected)
                        g.fill(ix - 2, iy, bookLeft + leftWidth - 4, iy + lineH, COLOR_ENTRY_SEL);
                    else if (hovered)
                        g.fill(ix - 2, iy, bookLeft + leftWidth - 4, iy + lineH, COLOR_CAT_HOVER);

                    String prefix = isExpanded ? "▾ " : "▸ ";
                    g.drawString(font, prefix + item.category().name(), ix, iy + 2, COLOR_CAT, false);
                }
                else if (item.entry() != null)
                {
                    boolean isSelected = item.entry().equals(selectedEntry);

                    if (isSelected)
                        g.fill(ix - 2, iy, bookLeft + leftWidth - 4, iy + lineH, COLOR_ENTRY_SEL);
                    else if (hovered)
                        g.fill(ix - 2, iy, bookLeft + leftWidth - 4, iy + lineH, COLOR_ENTRY_HOVER);

                    g.drawString(font, item.entry().name(), ix, iy + 2, COLOR_ENTRY_TEXT, false);
                }
            }
            baseY += lineH;
        }

        g.disableScissor();
    }

    private void renderPage(GuiGraphics g, GuidePage page, int mx, int my)
    {
        int cx = rightLeft + rightWidth / 2;
        int x0 = rightLeft + 12;
        int y0 = bookTop + 12;
        int maxW = rightWidth - 24;

        if (!page.title().isEmpty())
        {
            g.drawString(font, page.title(), cx - font.width(page.title()) / 2, y0, COLOR_TITLE, false);
            y0 += 14;
            g.fill(x0, y0, x0 + maxW, y0 + 1, COLOR_PAGE_EDGE);
            y0 += 6;
        }
        else
        {
            y0 += 4;
        }

        switch (page)
        {
            case GuidePage.Text text -> renderTextPage(g, text, x0, y0, maxW, mx, my);
            case GuidePage.ItemSpotlight is -> renderSpotlightPage(g, is, cx, y0, maxW, mx, my);
            case GuidePage.Entity ent -> renderEntityPage(g, ent, cx, y0, maxW, mx, my);
            case GuidePage.Recipe rec -> renderRecipePage(g, rec, cx, y0, maxW, mx, my);
        }
    }

    private void renderTextPage(GuiGraphics g, GuidePage.Text text, int x, int y, int maxW, int mx, int my)
    {
        if (!text.text().isEmpty())
        {
            var lines = font.split(Component.literal(text.text()), maxW);
            int lineY = y;
            g.enableScissor(x, y, x + maxW, bookTop + bookHeight - 86);
            for (var line : lines)
            {
                g.drawString(font, line, x, lineY, COLOR_TEXT, false);
                lineY += font.lineHeight + 1;
            }
            g.disableScissor();
        }

        if (!text.items().isEmpty())
        {
            int itemY = bookTop + bookHeight - 80;
            g.fill(x - 2, itemY - 4, x + maxW + 2, itemY + 26, COLOR_ITEM_BG);
            g.fill(x - 2, itemY - 4, x + maxW + 2, itemY - 3, COLOR_ITEM_BORDER);
            g.fill(x - 2, itemY + 25, x + maxW + 2, itemY + 26, COLOR_ITEM_BORDER);

            var refLabel = Component.translatable("gui.fatality.guide.related_items");
            g.drawString(font, refLabel, x, itemY - 2, COLOR_CAT, false);
            itemY += 11;

            int ix = x;
            for (var loc : text.items())
            {
                var item = BuiltInRegistries.ITEM.get(loc);
                if (item != null && item != BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace("air")))
                {
                    var stack = new ItemStack(item);
                    g.renderItem(stack, ix, itemY);
                    if (mx >= ix && mx < ix + 18 && my >= itemY && my < itemY + 18)
                        hoveredItemStack = stack;
                    ix += 20;
                    if (ix + 18 > x + maxW) break;
                }
            }
        }
    }

    private void renderSpotlightPage(GuiGraphics g, GuidePage.ItemSpotlight is, int cx, int y, int maxW, int mx, int my)
    {
        var item = BuiltInRegistries.ITEM.get(is.item());
        if (item != null && item != BuiltInRegistries.ITEM.get(ResourceLocation.withDefaultNamespace("air")))
        {
            var stack = new ItemStack(item);
            g.renderItem(stack, cx - 8, y);
            g.renderItemDecorations(font, stack, cx - 8, y);
            if (mx >= cx - 8 && mx < cx + 10 && my >= y && my < y + 18)
                hoveredItemStack = stack;

            var name = stack.getHoverName();
            g.drawString(font, name, cx - font.width(name) / 2, y + 22, COLOR_TITLE, false);

            if (!is.text().isEmpty())
            {
                int textY = y + 38;
                var lines = font.split(Component.literal(is.text()), maxW);
                for (var line : lines)
                {
                    g.drawString(font, line, rightLeft + 12, textY, COLOR_TEXT, false);
                    textY += font.lineHeight + 1;
                    if (textY > bookTop + bookHeight - 16) break;
                }
            }
        }
    }

    private void renderEntityPage(GuiGraphics g, GuidePage.Entity ent, int cx, int y, int maxW, int mx, int my)
    {
        var entityType = BuiltInRegistries.ENTITY_TYPE.get(ent.entity());
        if (entityType == null)
        {
            g.drawString(font, ent.entity().toString(), cx - font.width(ent.entity().toString()) / 2, y, COLOR_TITLE, false);
            if (!ent.text().isEmpty())
                renderEntityText(g, ent.text(), y + 14, maxW);
            return;
        }

        var name = entityType.getDescription();
        g.drawString(font, name, cx - font.width(name) / 2, y, COLOR_TITLE, false);

        var level = minecraft.level;
        if (level != null)
        {
            var entity = entityType.create(level);
            if (entity instanceof LivingEntity living)
            {
                int scale = Math.max(15, Math.min(35, (int) (30f / living.getBbHeight() * 1.5f)));
                living.yBodyRot = 180f;
                living.setYRot(180f);
                living.yHeadRot = 180f;
                living.yHeadRotO = 180f;
                int areaSize = 50;
                InventoryScreen.renderEntityInInventoryFollowsAngle(g, cx - areaSize, y, cx + areaSize, y + 100, scale, 0f, 0f, 0f, living);
            }
        }

        if (!ent.text().isEmpty())
            renderEntityText(g, ent.text(), y + 80, maxW);
    }

    private void renderEntityText(GuiGraphics g, String text, int y, int maxW)
    {
        int textY = y;
        var lines = font.split(Component.literal(text), maxW);
        for (var line : lines)
        {
            g.drawString(font, line, rightLeft + 12, textY, COLOR_TEXT, false);
            textY += font.lineHeight + 1;
            if (textY > bookTop + bookHeight - 16) break;
        }
    }

    private void renderRecipePage(GuiGraphics g, GuidePage.Recipe rec, int cx, int y, int maxW, int mx, int my)
    {
        var level = minecraft.level;
        if (level == null) return;

        var recipeManager = level.getRecipeManager();
        var holder = recipeManager.byKey(rec.recipe()).orElse(null);
        if (holder == null)
        {
            var label = Component.literal("§7" + rec.recipe().toString());
            g.drawString(font, label, cx - font.width(label) / 2, y, COLOR_TEXT, false);
            return;
        }

        var recipe = holder.value();

        //确定网格尺寸
        int gridW, gridH;
        List<Ingredient> ings;

        if (recipe instanceof ShapedRecipe shaped)
        {
            gridW = shaped.getWidth();
            gridH = shaped.getHeight();
            ings = shaped.getIngredients();
        }
        else if (recipe instanceof ShapelessRecipe shapeless)
        {
            ings = shapeless.getIngredients();
            int count = (int) ings.stream().filter(i -> !i.isEmpty()).count();
            if (count <= 1)     { gridW = 1; gridH = 1; }
            else if (count <= 4){ gridW = 2; gridH = 2; }
            else                { gridW = 3; gridH = 3; }
        }
        else
        {
            //没有合成配方，只显示输出
            var result = recipe.getResultItem(level.registryAccess());
            g.renderItem(result, cx - 8, y);
            g.renderItemDecorations(font, result, cx - 8, y);
            if (mx >= cx - 8 && mx < cx + 10 && my >= y && my < y + 18)
                hoveredItemStack = result;
            var name = result.getHoverName();
            g.drawString(font, name, cx - font.width(name) / 2, y + 22, COLOR_TITLE, false);
            return;
        }

        int slot = 18, gap = 2;
        int totalW = gridW * (slot + gap) - gap;
        int totalH = gridH * (slot + gap) - gap;

        //中心左侧的网格，右侧的箭头+输出
        int gridLeft = cx - totalW / 2 - 22;
        int gridTop = y;

        long time = level.getGameTime();
        int idx = 0;

        for (int row = 0; row < gridH; row++)
        {
            for (int col = 0; col < gridW; col++)
            {
                int sx = gridLeft + col * (slot + gap);
                int sy = gridTop + row * (slot + gap);
                drawSlot(g, sx, sy, slot);

                if (idx < ings.size() && !ings.get(idx).isEmpty())
                {
                    var items = ings.get(idx).getItems();
                    if (items.length > 0)
                    {
                        var stack = items[(int) ((time / 20) % items.length)];
                        g.renderItem(stack, sx + 1, sy + 1);
                        if (mx >= sx + 1 && mx < sx + 17 && my >= sy + 1 && my < sy + 17)
                            hoveredItemStack = stack;
                    }
                }
                idx++;
            }
        }

        //箭头
        int arrowX = gridLeft + totalW + 6;
        int arrowY = gridTop + totalH / 2 - 4;
        g.drawString(font, "→", arrowX + 2, arrowY, COLOR_TEXT);

        //输出
        var result = recipe.getResultItem(level.registryAccess());
        int outX = arrowX + 16;
        int outY = gridTop + totalH / 2 - 9;
        drawSlot(g, outX - 1, outY - 1, slot);
        g.renderItem(result, outX, outY);
        if (mx >= outX && mx < outX + 16 && my >= outY && my < outY + 16)
            hoveredItemStack = result;

        //输出物品的名字
        var name = result.getHoverName();
        int nameY = gridTop + totalH + 16;
        if (!rec.title().isEmpty())
            nameY = Math.max(nameY, y + 68);
        g.drawString(font, name, cx - font.width(name) / 2, nameY, COLOR_TITLE, false);
    }

    private static void drawSlot(GuiGraphics g, int x, int y, int size)
    {
        g.fill(x, y, x + size, y + size, 0xFF555555);
        g.fill(x + 1, y + 1, x + size - 1, y + size - 1, 0xFF8B8B8B);
    }

    private void renderPageNav(GuiGraphics g, int mx, int my)
    {
        if (selectedEntry == null || selectedEntry.pages().size() <= 1) return;

        int total = selectedEntry.pages().size();
        int btnY = bookTop + bookHeight - 18;
        int btnH = 12;

        if (currentPage > 0)
        {
            int btnX = rightLeft + 12;
            boolean hover = mx >= btnX && mx < btnX + 40 && my >= btnY && my < btnY + btnH;
            g.fill(btnX, btnY, btnX + 40, btnY + btnH, hover ? COLOR_CAT_HOVER : COLOR_BTN_BG);
            var prev = Component.literal("◀ ").append(Component.translatable("gui.fatality.guide.prev"));
            g.drawString(font, prev, btnX + 3, btnY + 2, COLOR_BTN_TEXT, false);
        }

        var pageInfo = Component.literal((currentPage + 1) + " / " + total);
        g.drawString(font, pageInfo, rightLeft + (rightWidth - font.width(pageInfo)) / 2, btnY + 2, COLOR_TEXT, false);

        if (currentPage < total - 1)
        {
            int btnX = rightLeft + rightWidth - 52;
            boolean hover = mx >= btnX && mx < btnX + 40 && my >= btnY && my < btnY + btnH;
            g.fill(btnX, btnY, btnX + 40, btnY + btnH, hover ? COLOR_CAT_HOVER : COLOR_BTN_BG);
            var next = Component.translatable("gui.fatality.guide.next").append(" ▶");
            g.drawString(font, next, btnX + 3, btnY + 2, COLOR_BTN_TEXT, false);
        }
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button)
    {
        if (button != 0) return super.mouseClicked(mx, my, button);

        //检查是否点击目录
        if (mx >= bookLeft + 3 && mx <= bookLeft + leftWidth - 3 && my >= bookTop + 20 && my <= bookTop + bookHeight - 3)
        {
            int index = (int) ((my - (bookTop + 20) + leftScrollOffset) / 13);
            if (index >= 0 && index < treeItems.size())
            {
                var item = treeItems.get(index);
                if (item.category() != null)
                    selectCategory(item.category());
                else if (item.entry() != null)
                    selectEntry(item.category(), item.entry());
                return true;
            }
        }

        if (selectedEntry != null && selectedEntry.pages().size() > 1)
        {
            int btnY = bookTop + bookHeight - 18;
            if (my >= btnY && my < btnY + 12)
            {
                if (currentPage > 0 && mx >= rightLeft + 12 && mx < rightLeft + 52)
                {
                    currentPage--;
                    return true;
                }
                if (currentPage < selectedEntry.pages().size() - 1 && mx >= rightLeft + rightWidth - 52 && mx < rightLeft + rightWidth - 12)
                {
                    currentPage++;
                    return true;
                }
            }
        }

        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double scrollX, double scrollY)
    {
        if (mx >= bookLeft && mx <= bookLeft + leftWidth && my >= bookTop && my <= bookTop + bookHeight)
        {
            int maxScroll = Math.max(0, leftContentHeight - (bookHeight - 23));
            leftScrollOffset = (int) Math.clamp(leftScrollOffset - scrollY * 13, 0, maxScroll);
            return true;
        }
        return super.mouseScrolled(mx, my, scrollX, scrollY);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
