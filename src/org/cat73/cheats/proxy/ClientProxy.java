package org.cat73.cheats.proxy;

import org.cat73.cheats.config.Config;
import org.cat73.cheats.gui.Gui;
import org.cat73.cheats.mods.ModManager;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(final FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    public void postInit(final FMLPostInitializationEvent event) {
        super.postInit(event);

        new ModManager();
        new Gui();
    }

    @Override
    public void preInit(final FMLPreInitializationEvent event) {
        super.preInit(event);

        new Config(event.getSuggestedConfigurationFile());
    }
}
