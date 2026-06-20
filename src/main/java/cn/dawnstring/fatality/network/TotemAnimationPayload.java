package cn.dawnstring.fatality.network;

import cn.dawnstring.fatality.Fatality;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record TotemAnimationPayload() implements CustomPacketPayload
{
    public static final CustomPacketPayload.Type<TotemAnimationPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.parse(Fatality.MODID + ":totem_animation"));

    public static final StreamCodec<RegistryFriendlyByteBuf, TotemAnimationPayload> STREAM_CODEC =
            StreamCodec.unit(new TotemAnimationPayload());

    @Override
    public CustomPacketPayload.Type<TotemAnimationPayload> type()
    {
        return TYPE;
    }
}
