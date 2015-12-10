package org.cat73.xray;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Configuration;

import org.cat73.xray.gui.Gui;
import org.cat73.xray.xray.Xray;

@Mod(modid = Cat_Xray.MODID, version = Cat_Xray.VERSION)
public class Cat_Xray {
    public static final String MODID = "Cat-Xray";
    public static final String VERSION = "0.1.2";
    public static final boolean DEBUG = true;
    @Instance()
    private static Cat_Xray instance;
    private Minecraft minecraft;
    private Configuration config;

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        if (event.getSide() == Side.SERVER) {
            return;
        }

        this.config = new Configuration(event.getSuggestedConfigurationFile());
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {
        if (event.getSide() == Side.SERVER) {
            return;
        }

        this.minecraft = Minecraft.getMinecraft();
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        if (event.getSide() == Side.SERVER) {
            return;
        }

        Xray.init();
        Gui.init();
    }

    public static Minecraft getMinecraft() {
        return Cat_Xray.instance.minecraft;
    }

    public static Configuration getConfig() {
        return Cat_Xray.instance.config;
    }
}
