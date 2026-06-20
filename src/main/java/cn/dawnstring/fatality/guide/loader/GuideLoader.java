package cn.dawnstring.fatality.guide.loader;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.guide.data.GuideCategory;
import cn.dawnstring.fatality.guide.data.GuideEntry;
import cn.dawnstring.fatality.guide.data.GuidePage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.util.*;

public class GuideLoader extends SimplePreparableReloadListener<Void>
{
    private static final String CATEGORIES_PATH = "guide/categories.json";
    private static final String ENTRIES_PREFIX = "guide/entries";

    private static Map<ResourceLocation, GuideCategory> categories = Map.of();
    private static Map<ResourceLocation, GuideEntry> entries = Map.of();

    @Override
    protected Void prepare(ResourceManager manager, ProfilerFiller profiler)
    {
        return null;
    }

    @Override
    protected void apply(Void data, ResourceManager manager, ProfilerFiller profiler)
    {
        load(manager);
    }

    public static Map<ResourceLocation, GuideCategory> getCategories()
    {
        return categories;
    }

    public static Map<ResourceLocation, GuideEntry> getEntries()
    {
        return entries;
    }

    @Nullable
    public static GuideEntry getEntry(ResourceLocation id)
    {
        return entries.get(id);
    }

    @Nullable
    public static GuideCategory getCategory(ResourceLocation id)
    {
        return categories.get(id);
    }

    public static void load(ResourceManager manager)
    {
        var gson = new Gson();
        var catMap = new HashMap<ResourceLocation, GuideCategory>();
        var entryMap = new HashMap<ResourceLocation, GuideEntry>();

        // 加载分类
        var catOpt = manager.getResource(ResourceLocation.fromNamespaceAndPath(Fatality.MODID, CATEGORIES_PATH));
        if (catOpt.isPresent())
        {
            try (var reader = catOpt.get().openAsReader())
            {
                var root = gson.fromJson(reader, JsonArray.class);
                for (var elem : root)
                {
                    var obj = elem.getAsJsonObject();
                    var category = parseCategory(obj);
                    if (category != null)
                        catMap.put(category.id(), category);
                }
            }
            catch (Exception e)
            {
                Fatality.LOGGER.error("Failed to load guide categories", e);
            }
        }

        // 加载条目
        var entryResources = manager.listResources(ENTRIES_PREFIX, path -> path.getPath().endsWith(".json"));
        for (var entry : entryResources.entrySet())
        {
            var path = entry.getKey();
            var res = entry.getValue();

            try (var reader = res.openAsReader())
            {
                var obj = gson.fromJson(reader, JsonObject.class);
                if (obj == null) continue;

                // 从路径提取 ID: guide/entries/xyz.json → xyz
                var pathStr = path.getPath();
                var idStr = pathStr.substring(ENTRIES_PREFIX.length() + 1, pathStr.length() - 5);
                var entryId = ResourceLocation.fromNamespaceAndPath(Fatality.MODID, idStr);

                var entryObj = parseEntry(entryId, obj);
                if (entryObj != null)
                    entryMap.put(entryId, entryObj);
            }
            catch (Exception e)
            {
                Fatality.LOGGER.error("Failed to load guide entry: {}", entry.getKey(), e);
            }
        }

        categories = Map.copyOf(catMap);
        entries = Map.copyOf(entryMap);
        Fatality.LOGGER.info("Loaded {} categories, {} entries", catMap.size(), entryMap.size());
    }

    private static GuideCategory parseCategory(JsonObject obj)
    {
        try
        {
            var id = ResourceLocation.parse(obj.get("id").getAsString());
            var name = obj.get("name").getAsString();
            var icon = ResourceLocation.parse(obj.get("icon").getAsString());
            var sortnum = obj.has("sortnum") ? obj.get("sortnum").getAsInt() : 0;
            var parentStr = obj.has("parent") ? obj.get("parent").getAsString() : null;
            var parentId = parentStr != null ? ResourceLocation.parse(parentStr) : null;

            var entryIds = new ArrayList<ResourceLocation>();
            if (obj.has("entries"))
            {
                for (var e : obj.get("entries").getAsJsonArray())
                    entryIds.add(ResourceLocation.parse(e.getAsString()));
            }

            return new GuideCategory(id, name, icon, sortnum, parentId, List.copyOf(entryIds));
        }
        catch (Exception e)
        {
            Fatality.LOGGER.error("Failed to parse category: {}", obj, e);
            return null;
        }
    }

    private static GuideEntry parseEntry(ResourceLocation id, JsonObject obj)
    {
        try
        {
            var name = obj.get("name").getAsString();
            var icon = ResourceLocation.parse(obj.get("icon").getAsString());
            var category = ResourceLocation.parse(obj.get("category").getAsString());
            var sortnum = obj.has("sortnum") ? obj.get("sortnum").getAsInt() : 0;

            var pages = new ArrayList<GuidePage>();
            if (obj.has("pages"))
            {
                for (var elem : obj.get("pages").getAsJsonArray())
                {
                    var page = parsePage(elem.getAsJsonObject());
                    if (page != null) pages.add(page);
                }
            }

            return new GuideEntry(id, name, icon, category, sortnum, List.copyOf(pages));
        }
        catch (Exception e)
        {
            Fatality.LOGGER.error("Failed to parse entry: {}", id, e);
            return null;
        }
    }

    private static GuidePage parsePage(JsonObject obj)
    {
        var type = obj.get("type").getAsString();
        var title = obj.has("title") ? obj.get("title").getAsString() : "";

        return switch (type)
        {
            case "text" ->
            {
                var text = obj.has("text") ? obj.get("text").getAsString() : "";
                var items = new ArrayList<ResourceLocation>();
                if (obj.has("items"))
                {
                    for (var e : obj.get("items").getAsJsonArray())
                        items.add(ResourceLocation.parse(e.getAsString()));
                }
                yield new GuidePage.Text(title, text, items);
            }
            case "item_spotlight" ->
            {
                var item = ResourceLocation.parse(obj.get("item").getAsString());
                var text = obj.has("text") ? obj.get("text").getAsString() : "";
                yield new GuidePage.ItemSpotlight(title, item, text);
            }
            case "entity" ->
            {
                var entity = ResourceLocation.parse(obj.get("entity").getAsString());
                var text = obj.has("text") ? obj.get("text").getAsString() : "";
                yield new GuidePage.Entity(title, entity, text);
            }
            case "recipe" ->
            {
                var recipe = ResourceLocation.parse(obj.get("recipe").getAsString());
                yield new GuidePage.Recipe(title, recipe);
            }
            default ->
            {
                Fatality.LOGGER.warn("Unknown guide page type: {}", type);
                yield null;
            }
        };
    }
}
