package cn.dawnstring.fatality.client.renderer.entity;

import cn.dawnstring.fatality.item.weapon.projectile.WeaponProjectile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

/**
 * 弹幕渲染器。
 * 支持两种渲染模式：
 * - billboard: 面向摄像机 (球体弹幕)
 * - 方向对齐平面: 法线 = cross(速度, 摄像机方向) (月牙形剑气)
 */
public class WeaponProjectileRenderer extends EntityRenderer<WeaponProjectile>
{
    private static final ResourceLocation MISSING = ResourceLocation.withDefaultNamespace("textures/missingno.png");

    public WeaponProjectileRenderer(EntityRendererProvider.Context ctx)
    {
        super(ctx);
    }

    @Override
    public void render(WeaponProjectile entity, float yaw, float partialTick,
                       PoseStack pose, MultiBufferSource buffer, int packedLight)
    {
        String textureId = entity.getTextureId();
        if (textureId == null || textureId.isEmpty()) return;

        ResourceLocation tex = ResourceLocation.tryParse(textureId);
        if (tex == null) return;

        Vec3 velocity = entity.getDeltaMovement();
        if (velocity.lengthSqr() < 0.0001) return;

        pose.pushPose();
        pose.translate(0, entity.getBbHeight() / 2, 0);

        float size = entity.getStats() != null ? entity.getStats().size() : 0.5f;

        // 速度方向
        Vec3 dir = velocity.normalize();

        // 判断渲染模式
        Vec3 cameraPos = entityRenderDispatcher.camera.getPosition();
        Vec3 toCamera = cameraPos.subtract(entity.position()).normalize();
        double dot = dir.dot(toCamera);

        if (Math.abs(dot) > 0.99)
        {
            // Billboard: 始终面向摄像机
            pose.mulPose(entityRenderDispatcher.cameraOrientation());
        }
        else
        {
            // 方向对齐平面：沿速度方向旋转
            float angleY = (float) Math.atan2(dir.x, dir.z);
            pose.mulPose(Axis.YP.rotation(angleY));

            float angleX = (float) Math.asin(dir.y);
            pose.mulPose(Axis.XP.rotation(angleX));
        }

        pose.scale(size, size, size);

        // 渲染四边形
        RenderType renderType = RenderType.entityCutout(tex);
        VertexConsumer vc = buffer.getBuffer(renderType);

        var poseMat = pose.last();
        Matrix4f mat = poseMat.pose();

        float h = 0.5f;
        vc.addVertex(mat, -h, -h, 0).setColor(255, 255, 255, 255).setUv(0, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseMat, 0, 0, 1);
        vc.addVertex(mat, h, -h, 0).setColor(255, 255, 255, 255).setUv(1, 1).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseMat, 0, 0, 1);
        vc.addVertex(mat, h, h, 0).setColor(255, 255, 255, 255).setUv(1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseMat, 0, 0, 1);
        vc.addVertex(mat, -h, h, 0).setColor(255, 255, 255, 255).setUv(0, 0).setOverlay(OverlayTexture.NO_OVERLAY).setLight(packedLight).setNormal(poseMat, 0, 0, 1);

        pose.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(WeaponProjectile entity)
    {
        return MISSING;
    }
}
