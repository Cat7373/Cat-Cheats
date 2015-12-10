package org.cat73.xray;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.cat73.xray.proxy.Proxy;

@Mod(modid = Cat_Xray.MODID, version = Cat_Xray.VERSION)
public class Cat_Xray {
    public static final String MODID = "Cat-Xray";
    public static final String VERSION = "0.2.0";
    public static final boolean DEBUG = true;

    @Instance()
    private static Cat_Xray instance;
    @SidedProxy(serverSide="org.cat73.xray.proxy.ServerProxy", clientSide = "org.cat73.xray.proxy.ClientProxy")
    public static Proxy proxy;

    @EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @EventHandler
    public void init(final FMLInitializationEvent event) {
        proxy.init(event);
    }

    @EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
}
