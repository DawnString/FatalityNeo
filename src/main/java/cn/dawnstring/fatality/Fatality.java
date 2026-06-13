package cn.dawnstring.fatality;

import cn.dawnstring.fatality.command.FatalityCommand;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

@Mod(Fatality.MODID)
public class Fatality
{
    public static final String MODID = "fatality";
    public static final String VERSION = "1.0.0";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Fatality(IEventBus modEventBus, ModContainer modContainer)
    {
        LOGGER.info("Fatality initialized");
    }

    private void commonSetup(FMLCommonSetupEvent event)
    {
        event.enqueueWork(() -> {
            LOGGER.info("Fatality common setup");
        });
    }

    private void clientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            LOGGER.info("Fatality client setup");
        });
    }
}