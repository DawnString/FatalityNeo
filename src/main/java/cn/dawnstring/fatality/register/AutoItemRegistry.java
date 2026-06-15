package cn.dawnstring.fatality.register;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.item.ItemCategory;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforgespi.language.ModFileScanData;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoItemRegistry
{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, Fatality.MODID);

    public static final Map<ItemCategory, List<Item>> CATEGORY_ITEMS = new HashMap<>();

    public static void registerItems(IEventBus eventBus)
    {
        ModList.get().getModContainerById(Fatality.MODID).ifPresent(modContainer ->
        {
            modContainer.getModInfo().getOwningFile().getFile().getScanResult()
                    .getAnnotatedBy(AutoItem.class, ElementType.TYPE)
                    .forEach(annotationData ->
                    {
                        var clazz = loadClass(annotationData);
                        var annotation = clazz.getAnnotation(AutoItem.class);

                        ITEMS.register(annotation.itemId(), ()->createInstance(clazz));
                        CATEGORY_ITEMS.computeIfAbsent(annotation.category(), k-> new ArrayList<>());
                    });
        });

        ITEMS.register(eventBus);
    }

    private static Class<? extends Item> loadClass(ModFileScanData.AnnotationData data)
    {
        try
        {
            String className = data.clazz().getClassName();
            Class<?> clazz = Class.forName(className, true,
                    Thread.currentThread().getContextClassLoader());
            return clazz.asSubclass(Item.class);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to load item class", e);
        }
    }

    private static Item createInstance(Class<? extends Item> clazz)
    {
        try
        {
            return clazz.getDeclaredConstructor().newInstance();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Failed to instantiate " + clazz.getSimpleName(), e);
        }
    }
}
