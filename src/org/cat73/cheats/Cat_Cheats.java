package org.cat73.cheats;

import org.cat73.cheats.proxy.CommonProxy;
import org.cat73.cheats.reference.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, version = Reference.VERSION, useMetadata = true)
public class Cat_Cheats {
    @Mod.Instance(Reference.MODID)
    private static Cat_Cheats instance;

    @SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
    private static CommonProxy proxy;

    public static Cat_Cheats instance() {
        return Cat_Cheats.instance;
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        Cat_Cheats.proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        Cat_Cheats.proxy.postInit(event);
    }

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        Cat_Cheats.proxy.preInit(event);
    }
}
