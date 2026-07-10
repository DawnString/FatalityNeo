package cn.dawnstring.fatality.network;

import cn.dawnstring.fatality.Fatality;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * C2S 包，携带 jumping/sneaking/forwardImpulse/leftImpulse
 * @param jumping
 * @param sneaking
 * @param forwardImpulse
 * @param leftImpulse
 */
public record PlayerInputPayload(boolean jumping, boolean sneaking, float forwardImpulse, float leftImpulse)
        implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<PlayerInputPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "player_input"));

    public static final StreamCodec<ByteBuf, PlayerInputPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL, PlayerInputPayload::jumping,
            ByteBufCodecs.BOOL, PlayerInputPayload::sneaking,
            ByteBufCodecs.FLOAT, PlayerInputPayload::forwardImpulse,
            ByteBufCodecs.FLOAT, PlayerInputPayload::leftImpulse,
            PlayerInputPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
