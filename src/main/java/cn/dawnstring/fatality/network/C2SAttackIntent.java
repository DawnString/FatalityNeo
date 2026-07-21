package cn.dawnstring.fatality.network;

import cn.dawnstring.fatality.Fatality;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * C2S 攻击意图包。客户端拦截攻击键后发送，服务端 WeaponHandler 路由处理。
 *
 * @param actionType 0=左键攻击, 1=右键技能, 2=松开(持续型)
 * @param attackMode 客户端期望的攻击模式，服务端做二次校验
 * @param timestamp  客户端时间戳，防刷
 * @param cursorX    SWING 模式专用：准星瞄准位置 X
 * @param cursorY    SWING 模式专用：准星瞄准位置 Y
 * @param cursorZ    SWING 模式专用：准星瞄准位置 Z
 * @param dirX       PROJECTILE 模式专用：弹幕方向 X
 * @param dirY       PROJECTILE 模式专用：弹幕方向 Y
 * @param dirZ       PROJECTILE 模式专用：弹幕方向 Z
 * @param chargeTime PROJECTILE 模式专用：蓄力 tick
 * @param isPressed  BEAM 模式专用：true=按下, false=松开
 */
public record C2SAttackIntent(
        byte actionType,
        byte attackMode,
        int timestamp,
        double cursorX,
        double cursorY,
        double cursorZ,
        float dirX,
        float dirY,
        float dirZ,
        int chargeTime,
        boolean isPressed
) implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<C2SAttackIntent> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "attack_intent"));

    public static final StreamCodec<ByteBuf, C2SAttackIntent> STREAM_CODEC = new StreamCodec<>()
    {
        @Override
        public C2SAttackIntent decode(ByteBuf buf)
        {
            return new C2SAttackIntent(
                    buf.readByte(), buf.readByte(), buf.readInt(),
                    buf.readDouble(), buf.readDouble(), buf.readDouble(),
                    buf.readFloat(), buf.readFloat(), buf.readFloat(),
                    buf.readInt(), buf.readBoolean()
            );
        }

        @Override
        public void encode(ByteBuf buf, C2SAttackIntent p)
        {
            buf.writeByte(p.actionType());
            buf.writeByte(p.attackMode());
            buf.writeInt(p.timestamp());
            buf.writeDouble(p.cursorX());
            buf.writeDouble(p.cursorY());
            buf.writeDouble(p.cursorZ());
            buf.writeFloat(p.dirX());
            buf.writeFloat(p.dirY());
            buf.writeFloat(p.dirZ());
            buf.writeInt(p.chargeTime());
            buf.writeBoolean(p.isPressed());
        }
    };

    // 左键攻击 (SWING)
    public static C2SAttackIntent swing(double cx, double cy, double cz)
    {
        return new C2SAttackIntent((byte) 0, (byte) 0, (int) (System.currentTimeMillis() & 0x7FFFFFFF),
                cx, cy, cz, 0, 0, 0, 0, false);
    }

    // 左键攻击 (PROJECTILE)
    public static C2SAttackIntent projectile(float dx, float dy, float dz, int charge)
    {
        return new C2SAttackIntent((byte) 0, (byte) 1, (int) (System.currentTimeMillis() & 0x7FFFFFFF),
                0, 0, 0, dx, dy, dz, charge, false);
    }

    // 左键攻击 (BEAM) 按下
    public static C2SAttackIntent beamPress()
    {
        return new C2SAttackIntent((byte) 0, (byte) 2, (int) (System.currentTimeMillis() & 0x7FFFFFFF),
                0, 0, 0, 0, 0, 0, 0, true);
    }

    // 左键攻击 (BEAM) 松开
    public static C2SAttackIntent beamRelease()
    {
        return new C2SAttackIntent((byte) 2, (byte) 2, (int) (System.currentTimeMillis() & 0x7FFFFFFF),
                0, 0, 0, 0, 0, 0, 0, false);
    }

    // 右键技能
    public static C2SAttackIntent active()
    {
        return new C2SAttackIntent((byte) 1, (byte) 0, (int) (System.currentTimeMillis() & 0x7FFFFFFF),
                0, 0, 0, 0, 0, 0, 0, false);
    }

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
