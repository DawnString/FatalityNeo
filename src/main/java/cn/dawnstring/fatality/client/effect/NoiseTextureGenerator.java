package cn.dawnstring.fatality.client.effect;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;

public class NoiseTextureGenerator
{
    private static final ResourceLocation SHIELD_NOISE = ResourceLocation.parse("fatality:shield_noise");
    private static DynamicTexture texture;
    private static long lastUpdateGameTime = -1;

    private static final int SIZE = 128;
    private static final float FBM_SCALE = 1f / 6f;
    private static final int OCTAVES = 4;
    private static final float CONTRAST = 1.3f;
    private static final float BRIGHTNESS = 0.5f;
    private static final float SPEED = 0.02f;
    private static final int SEED = 42;

    public static ResourceLocation getOrCreate()
    {
        if (texture == null)
        {
            create();
        }
        return SHIELD_NOISE;
    }

    public static void tick()
    {
        if (texture == null) return;
        var level = Minecraft.getInstance().level;
        if (level == null) return;

        long gameTime = level.getGameTime();
        if (gameTime == lastUpdateGameTime) return;
        lastUpdateGameTime = gameTime;

        update(gameTime);
    }

    private static void create()
    {
        texture = new DynamicTexture(SIZE, SIZE, false);
        update(0);
        Minecraft.getInstance().getTextureManager().register(SHIELD_NOISE, texture);
    }

    private static void update(long gameTime)
    {
        NativeImage image = texture.getPixels();
        float time = gameTime * SPEED;

        for (int x = 0; x < SIZE; x++)
        {
            for (int y = 0; y < SIZE; y++)
            {
                float nx = x * FBM_SCALE + time;
                float ny = y * FBM_SCALE + time * 0.7f;
                float noise = fbm(nx, ny, OCTAVES, SEED);
                float stretched = (noise - 0.5f) * CONTRAST + BRIGHTNESS;
                int alpha = Math.round(Math.max(0, Math.min(1, stretched)) * 255);
                int pixel = (alpha << 24) | 0x00FFFFFF;
                image.setPixelRGBA(x, y, pixel);
            }
        }

        texture.upload();
    }

    private static float hash(int x, int y, int seed)
    {
        int n = x + y * 57 + seed;
        n = (n << 13) ^ n;
        return ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7FFFFFFF) / (float) Integer.MAX_VALUE;
    }

    private static float smoothNoise(float x, float y, int seed)
    {
        int ix = (int) Math.floor(x);
        int iy = (int) Math.floor(y);
        float fx = x - ix;
        float fy = y - iy;
        fx = fx * fx * (3.0f - 2.0f * fx);
        fy = fy * fy * (3.0f - 2.0f * fy);

        float v00 = hash(ix, iy, seed);
        float v10 = hash(ix + 1, iy, seed);
        float v01 = hash(ix, iy + 1, seed);
        float v11 = hash(ix + 1, iy + 1, seed);

        return v00 + (v10 - v00) * fx + (v01 - v00) * fy + (v11 - v10 - v01 + v00) * fx * fy;
    }

    private static float fbm(float x, float y, int octaves, int seed)
    {
        float value = 0;
        float amplitude = 0.5f;
        float frequency = 1;
        for (int i = 0; i < octaves; i++)
        {
            value += amplitude * smoothNoise(x * frequency, y * frequency, seed + i * 100);
            amplitude *= 0.5f;
            frequency *= 2;
        }
        return value;
    }
}
