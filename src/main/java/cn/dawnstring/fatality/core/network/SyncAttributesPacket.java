package cn.dawnstring.fatality.core.network;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.core.capability.PlayerAttributes;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.jpountz.util.ByteBufferUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record SyncAttributesPacket(PlayerAttributes attributes) implements CustomPacketPayload
{
    private static final HolderLookup.Provider EMPTY = RegistryAccess.EMPTY;

    public static final Type<SyncAttributesPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(Fatality.MODID, "sync_attributes"));

    private static final StreamCodec<ByteBuf, CompoundTag> ATTRIBUTES_TAG = StreamCodec.of(
            (buf, tag) -> new FriendlyByteBuf(buf).writeNbt(tag),
            buf -> new FriendlyByteBuf(buf).readNbt()
    );

    public static final StreamCodec<ByteBuf, SyncAttributesPacket> STREAM_CODEC = StreamCodec.composite(
            ATTRIBUTES_TAG,
            syncAttributesPacket -> syncAttributesPacket.attributes().serializeNBT(EMPTY),
            compoundTag ->
            {
                PlayerAttributes attributes = new PlayerAttributes();
                attributes.deserializeNBT(EMPTY, compoundTag);
                return new SyncAttributesPacket(attributes);
            }
    );

    @Override
    public Type<? extends CustomPacketPayload> type()
    {
        return TYPE;
    }
}
