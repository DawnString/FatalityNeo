package cn.dawnstring.fatality.core.register;

import cn.dawnstring.fatality.Fatality;
import cn.dawnstring.fatality.client.effect.PlayerEffectLayer;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryUtil;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class ClientModEvents
{
    public static void onAddLayers(EntityRenderersEvent.AddLayers event)
    {
        PlayerRenderer defaultRenderer = event.getSkin(PlayerSkin.Model.WIDE);
        if (defaultRenderer != null)
            defaultRenderer.addLayer(new PlayerEffectLayer(defaultRenderer));

        PlayerRenderer slimRenderer = event.getSkin(PlayerSkin.Model.SLIM);
        if (slimRenderer != null)
            slimRenderer.addLayer(new PlayerEffectLayer(slimRenderer));
    }

    //游戏图标
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            try {
                var mc = Minecraft.getInstance();
                long window = mc.getWindow().getWindow();

                String[] sizes = {"16", "32", "48", "128", "256"};
                GLFWImage.Buffer iconBuffer = GLFWImage.malloc(sizes.length);
                List<ByteBuffer> allocatedBufs = new java.util.ArrayList<>();
                int count = 0;

                for (String size : sizes)
                {
                    String path = "/assets/fatality/icons/icon_" + size + "x" + size + ".png";
                    try (InputStream in = ClientModEvents.class.getResourceAsStream(path))
                    {
                        if (in == null) continue;

                        var nativeImg = NativeImage.read(in);
                        int w = nativeImg.getWidth();
                        int h = nativeImg.getHeight();

                        ByteBuffer pixelBuf = MemoryUtil.memAlloc(w * h * 4);
                        allocatedBufs.add(pixelBuf);
                        for (int y = 0; y < h; y++)
                        {
                            for (int x = 0; x < w; x++)
                            {
                                int argb = nativeImg.getPixelRGBA(x, y);
                                pixelBuf.put((byte) ((argb >> 16) & 0xFF));  // R
                                pixelBuf.put((byte) ((argb >> 8) & 0xFF));   // G
                                pixelBuf.put((byte) (argb & 0xFF));           // B
                                pixelBuf.put((byte) ((argb >> 24) & 0xFF));  // A
                            }
                        }
                        pixelBuf.flip();

                        var glfwImg = GLFWImage.create();
                        glfwImg.set(w, h, pixelBuf);
                        iconBuffer.put(count++, glfwImg);

                        nativeImg.close();
                    }
                }

                if (count > 0)
                {
                    iconBuffer.limit(count);
                    iconBuffer.position(0);
                    GLFW.glfwSetWindowIcon(window, iconBuffer);
                    Fatality.LOGGER.info("Custom window icon set ({} sizes)", count);
                }
                else
                {
                    Fatality.LOGGER.warn("No custom window icons found in assets/fatality/icons/");
                }

                iconBuffer.free();
                for (var buf : allocatedBufs) MemoryUtil.memFree(buf);
            }
            catch (Exception e)
            {
                Fatality.LOGGER.error("Failed to set custom window icon", e);
            }
        });
    }
}
