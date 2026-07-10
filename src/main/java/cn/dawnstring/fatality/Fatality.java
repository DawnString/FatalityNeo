package cn.dawnstring.fatality;

import cn.dawnstring.fatality.guide.loader.GuideLoader;
import cn.dawnstring.fatality.core.input.PlayerInputState;
import cn.dawnstring.fatality.network.PlayerInputPayload;
import cn.dawnstring.fatality.network.TotemAnimationPayload;
import cn.dawnstring.fatality.register.AutoItemRegistry;
import cn.dawnstring.fatality.register.ModCreativeTabs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

@Mod(Fatality.MODID)
public class Fatality
{
    public static final String MODID = "fatality";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final String GIVEN_TAG = "fatality:given_final_words";

    public Fatality(IEventBus modEventBus, ModContainer modContainer)
    {
        AutoItemRegistry.registerItems(modEventBus);
        ModCreativeTabs.register(modEventBus);

        modEventBus.addListener(RegisterPayloadHandlersEvent.class, event ->
        {
            var registrar = event.registrar(Fatality.MODID);
            registrar.playToClient(TotemAnimationPayload.TYPE, TotemAnimationPayload.STREAM_CODEC,
                    (payload, context) ->
                    {
                        var mc = net.minecraft.client.Minecraft.getInstance();
                        mc.gameRenderer.displayItemActivation(
                                new ItemStack(BuiltInRegistries.ITEM.get(
                                        ResourceLocation.parse("fatality:final_words"))));
                        if (mc.player != null)
                            mc.player.playSound(net.minecraft.sounds.SoundEvents.TOTEM_USE, 1.0f, 1.0f);
                    });
            registrar.playToServer(
                    PlayerInputPayload.TYPE,
                    PlayerInputPayload.STREAM_CODEC,
                    (payload, context) ->
                    {
                        var player = context.player();
                        PlayerInputState.update(player.getUUID(),
                                payload.jumping(), payload.sneaking(),
                                payload.forwardImpulse(), payload.leftImpulse());
                    });
        });

        NeoForge.EVENT_BUS.addListener(AddReloadListenerEvent.class, event ->
                event.addListener(new GuideLoader()));

        NeoForge.EVENT_BUS.addListener(Fatality::onFirstWood);

        LOGGER.info("Fatality initialized");
    }

    private static void onFirstWood(ItemEntityPickupEvent.Post event)
    {
        if (event.getPlayer() == null) return;
        var player = (ServerPlayer) event.getPlayer();
        if (player.level().isClientSide()) return;

        var stack = event.getOriginalStack();
        if (!stack.is(ItemTags.LOGS)) return;

        var data = player.getPersistentData();
        if (data.getBoolean(GIVEN_TAG)) return;

        var bookStack = new ItemStack(BuiltInRegistries.ITEM.get(
                ResourceLocation.parse("fatality:final_words")));
        if (bookStack.isEmpty()) return;

        if (player.getInventory().add(bookStack))
        {
            data.putBoolean(GIVEN_TAG, true);
            PacketDistributor.sendToPlayer(player, new TotemAnimationPayload());
        }
    }
}