package cn.dawnstring.fatality.network;

import cn.dawnstring.fatality.Fatality;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * C2S 包，携带 jumping/sneaking/forwardImpulse/leftImpulse<br>
 * dashDirection: -1=无动作, 0=FORWARD, 1=BACKWARD, 2=LEFT, 3=RIGHT（客户端双击检测填充）
 * @param jumping
 * @param sneaking
 * @param forwardImpulse
 * @param leftImpulse
 * @param dashDirection
 */
public record PlayerInputPayload(boolean jumping, boolean sneaking, float forwardImpulse, float leftImpulse, int dashDirection)
        implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PlayerInputPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "player_input"));

    public static final StreamCodec<ByteBuf, PlayerInputPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, PlayerInputPayload::jumping,
            ByteBufCodecs.BOOL, PlayerInputPayload::sneaking,
            ByteBufCodecs.FLOAT, PlayerInputPayload::forwardImpulse,
            ByteBufCodecs.FLOAT, PlayerInputPayload::leftImpulse,
            ByteBufCodecs.VAR_INT, PlayerInputPayload::dashDirection,
            PlayerInputPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
