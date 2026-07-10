package cn.dawnstring.fatality.client.effect;

import cn.dawnstring.fatality.utils.PlayerEffectUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class PlayerEffectLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>
{
    public PlayerEffectLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer)
    {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       AbstractClientPlayer player, float limbSwing, float limbSwingAmount,
                       float partialTick, float ageInTicks, float netHeadYaw, float headPitch)
    {
        int color = PlayerEffectUtil.getRenderColor(player.getUUID(), ageInTicks);
        if (color == 0) return;

        // 每 tick 更新一次噪声纹理，产生流动动画
        NoiseTextureGenerator.tick();

        // 默认使用噪声纹理获得能量护盾效果，饰品可传入自定义纹理覆盖
        ResourceLocation tex = PlayerEffectUtil.getRenderTexture(player.getUUID(), NoiseTextureGenerator.getOrCreate());
        RenderType renderType = RenderType.entityTranslucent(tex);
        VertexConsumer vertexConsumer = buffer.getBuffer(renderType);

        poseStack.pushPose();
        // 模型略微放大，使特效浮在玩家表面形成护盾层
        float scale = 1.03f;
        poseStack.scale(scale, scale, scale);
        getParentModel().renderToBuffer(poseStack, vertexConsumer,
                LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, color);
        poseStack.popPose();
    }
}
