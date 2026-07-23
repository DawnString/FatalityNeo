package cn.dawnstring.fatality;

import cn.dawnstring.fatality.client.gui.hud.HudOverlayRegistry;
import cn.dawnstring.fatality.core.combat.WeaponHandler;
import cn.dawnstring.fatality.guide.loader.GuideLoader;
import cn.dawnstring.fatality.item.weapon.WeaponItem;
import cn.dawnstring.fatality.core.accessory.AccessoryManager;
import cn.dawnstring.fatality.core.input.PlayerInputState;
import cn.dawnstring.fatality.core.register.ClientModEvents;
import cn.dawnstring.fatality.item.accessory.BaseShieldItem;
import cn.dawnstring.fatality.item.accessory.Direction;
import cn.dawnstring.fatality.network.C2SAttackIntent;
import cn.dawnstring.fatality.network.PlayerInputPayload;
import cn.dawnstring.fatality.network.SyncEffectPayload;
import cn.dawnstring.fatality.network.TotemAnimationPayload;
import cn.dawnstring.fatality.item.armor.ModArmorMaterials;
import cn.dawnstring.fatality.register.AutoItemRegistry;
import cn.dawnstring.fatality.register.ModCreativeTabs;
import cn.dawnstring.fatality.register.ModEntityTypes;
import cn.dawnstring.fatality.utils.PlayerEffectUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
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
        ModArmorMaterials.register(modEventBus);
        AutoItemRegistry.registerItems(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModEntityTypes.register(modEventBus);

        modEventBus.addListener(RegisterGuiLayersEvent.class, HudOverlayRegistry::onRegisterLayers);

        modEventBus.addListener(EntityRenderersEvent.AddLayers.class, ClientModEvents::onAddLayers);

        modEventBus.addListener(EntityRenderersEvent.RegisterRenderers.class, ClientModEvents::onRegisterRenderers);

        modEventBus.addListener(FMLClientSetupEvent.class, ClientModEvents::onClientSetup);

        modEventBus.addListener(RegisterPayloadHandlersEvent.class, event ->
        {
            var registrar = event.registrar(Fatality.MODID);

            //终末之言的动画
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

            //玩家输入检测（内含双击冲刺标记）
            registrar.playToServer(
                    PlayerInputPayload.TYPE,
                    PlayerInputPayload.STREAM_CODEC,
                    (payload, context) ->
                    {
                        var player = context.player();
                        PlayerInputState.update(player.getUUID(),
                                payload.jumping(), payload.sneaking(),
                                payload.forwardImpulse(), payload.leftImpulse());

                        // 客户端检测到双击方向键，触发冲刺
                        if (payload.dashDirection() >= 0)
                        {
                            boolean found = false;
                            var inv = AccessoryManager.getInventory(player);
                            for (int i = 0; i < AccessoryManager.SLOT_COUNT; i++)
                            {
                                ItemStack stack = inv.getItem(i);
                                if (stack.getItem() instanceof BaseShieldItem shield)
                                {
                                    shield.startDash(player, Direction.values()[payload.dashDirection()]);
                                    found = true;
                                    break;
                                }
                            }
                            if (!found)
                                Fatality.LOGGER.warn("DashPayload ignored: no BaseShieldItem found for {}", player.getName().getString());
                        }
                    });

            //武器攻击意图
            registrar.playToServer(
                    C2SAttackIntent.TYPE,
                    C2SAttackIntent.STREAM_CODEC,
                    (payload, context) -> WeaponHandler.dispatch(context.player(), payload));

            //玩家光效同步
            registrar.playToClient(
                    SyncEffectPayload.TYPE,
                    SyncEffectPayload.STREAM_CODEC,
                    (payload, context) ->
                    {
                        PlayerEffectUtil.apply(payload.playerUuid(), payload.texture(),
                                payload.color(), payload.pulseSpeed());
                    });

        });

        NeoForge.EVENT_BUS.addListener(AddReloadListenerEvent.class, event ->
                event.addListener(new GuideLoader()));

        NeoForge.EVENT_BUS.addListener(Fatality::onFirstWood);

        NeoForge.EVENT_BUS.addListener(HudOverlayRegistry::onRenderGuiLayerPre);

        NeoForge.EVENT_BUS.addListener(ServerTickEvent.Post.class, event ->
        {
            var server = event.getServer();
            if (server == null) return;
            for (var player : server.getPlayerList().getPlayers())
            {
                var held = player.getMainHandItem();
                if (held.getItem() instanceof WeaponItem weapon)
                    weapon.onServerTick(player);
            }
        });

        LOGGER.info("Fatality initialized");
    }

    private static void onFirstWood(ItemEntityPickupEvent.Post event)
    {
        if (event.getPlayer() == null) return;
        var player = (ServerPlayer) event.getPlayer();

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