package cn.dawnstring.fatality.register;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.item.ItemCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs
{
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Fatality.MODID);

    public static void register(IEventBus eventBus)
    {
        for (var category : ItemCategory.values())
        {
            var items = AutoItemRegistry.CATEGORY_ITEMS.get(category);
            TABS.register(category.name().toLowerCase(), () -> CreativeModeTab.builder()
                    .title(Component.translatable("creativetab.fatality." + category.name().toLowerCase()))
                    .icon(() ->
                    {
                        if (items != null && !items.isEmpty())
                            return new ItemStack(items.getFirst().get());
                        return ItemStack.EMPTY;
                    })
                    .displayItems((params, output) ->
                    {
                        if (items != null)
                            items.forEach(reg -> output.accept(reg.get()));
                    })
                    .build());
        }

        TABS.register(eventBus);
    }
}
