package cn.dawnstring.fatality.client.gui;

import cn.dawnstring.fatality.guide.FinalWordsScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientGuideScreenOpener
{
    public static void open()
    {
        Minecraft.getInstance().setScreen(new FinalWordsScreen());
    }
}
