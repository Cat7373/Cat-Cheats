package org.cat73.xray.proxy;

import org.cat73.xray.config.Config;
import org.cat73.xray.gui.Gui;
import org.cat73.xray.xray.Xray;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements CommonProxy {
    @Override
    public void preInit(final FMLPreInitializationEvent event) {
        Config.init(event.getSuggestedConfigurationFile());
    }

    @Override
    public void init(final FMLInitializationEvent event) {
    }

    @Override
    public void postInit(final FMLPostInitializationEvent event) {
        Xray.init();
        Gui.init();
    }
}
