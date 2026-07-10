package cn.dawnstring.fatality.network;

import cn.dawnstring.fatality.Fatality;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public record SyncEffectPayload(UUID playerUuid, ResourceLocation texture, int color, float pulseSpeed)
        implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<SyncEffectPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.parse(Fatality.MODID + ":sync_effect"));

    private static final StreamCodec<ByteBuf, UUID> UUID_STREAM_CODEC = StreamCodec.of(
            (buf, uuid) -> { buf.writeLong(uuid.getMostSignificantBits()); buf.writeLong(uuid.getLeastSignificantBits()); },
            buf -> new UUID(buf.readLong(), buf.readLong())
    );

    private static final StreamCodec<ByteBuf, ResourceLocation> NULLABLE_TEXTURE_CODEC = StreamCodec.of(
            (buf, tex) -> {
                buf.writeBoolean(tex != null);
                if (tex != null) ResourceLocation.STREAM_CODEC.encode(buf, tex);
            },
            buf -> buf.readBoolean() ? ResourceLocation.STREAM_CODEC.decode(buf) : null
    );

    public static final StreamCodec<ByteBuf, SyncEffectPayload> STREAM_CODEC = StreamCodec.composite(
            UUID_STREAM_CODEC, SyncEffectPayload::playerUuid,
            NULLABLE_TEXTURE_CODEC, SyncEffectPayload::texture,
            ByteBufCodecs.INT, SyncEffectPayload::color,
            ByteBufCodecs.FLOAT, SyncEffectPayload::pulseSpeed,
            SyncEffectPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
