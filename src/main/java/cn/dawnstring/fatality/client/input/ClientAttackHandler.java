package cn.dawnstring.fatality.client.input;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.item.weapon.AttackMode;
import cn.dawnstring.fatality.item.weapon.WeaponItem;
import cn.dawnstring.fatality.network.C2SAttackIntent;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

/**
 * 客户端攻击拦截。
 * 在 ClientTickEvent.Post 中检测攻击键/使用键的状态变化，
 * 持有 WeaponItem 时发送 C2SAttackIntent 并由服务端 WeaponHandler 处理。
 *
 * 使用 InputEvent.InteractionKeyMappingTriggered 取消原版攻击行为，
 * 使 startAttack/startUseItem 不被调用。
 */
@EventBusSubscriber(modid = Fatality.MODID, value = Dist.CLIENT)
public class ClientAttackHandler
{
    private static boolean wasAttackDown = false;
    private static boolean wasUseDown = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event)
    {
        var mc = Minecraft.getInstance();
        if (mc.player == null) return;

        var held = mc.player.getMainHandItem();
        if (!(held.getItem() instanceof WeaponItem weapon))
        {
            wasAttackDown = mc.options.keyAttack.isDown();
            wasUseDown = mc.options.keyUse.isDown();
            return;
        }

        // 左键攻击 — 上升沿检测
        boolean isAttackDown = mc.options.keyAttack.isDown();
        if (isAttackDown && !wasAttackDown)
            sendAttackIntent(mc.player, weapon);

        // 左键攻击 — 下降沿检测 (BEAM 模式专用)
        if (!isAttackDown && wasAttackDown
                && weapon.getWeaponStats().attackMode() == AttackMode.BEAM)
            PacketDistributor.sendToServer(C2SAttackIntent.beamRelease());

        wasAttackDown = isAttackDown;

        // 右键 — 上升沿检测，仅当武器有主动技能时
        boolean isUseDown = mc.options.keyUse.isDown();
        if (isUseDown && !wasUseDown && weapon.hasActiveAbility())
            PacketDistributor.sendToServer(C2SAttackIntent.active());

        wasUseDown = isUseDown;
    }

    private static void sendAttackIntent(net.minecraft.world.entity.player.Player player, WeaponItem weapon)
    {
        switch (weapon.getWeaponStats().attackMode())
        {
            case SWING ->
            {
                var hit = player.pick(6.0, 0, false);
                var pos = hit.getLocation();
                PacketDistributor.sendToServer(C2SAttackIntent.swing(pos.x, pos.y, pos.z));
            }
            case PROJECTILE ->
            {
                var look = player.getLookAngle();
                PacketDistributor.sendToServer(C2SAttackIntent.projectile(
                        (float) look.x, (float) look.y, (float) look.z, 0));
            }
            case BEAM ->
            {
                PacketDistributor.sendToServer(C2SAttackIntent.beamPress());
            }
            case SPECIAL ->
            {
                var look = player.getLookAngle();
                PacketDistributor.sendToServer(C2SAttackIntent.projectile(
                        (float) look.x, (float) look.y, (float) look.z, 0));
            }
        }
    }
}
