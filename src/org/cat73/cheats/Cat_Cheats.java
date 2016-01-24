package org.cat73.cheats;

import org.cat73.cheats.proxy.CommonProxy;
import org.cat73.cheats.reference.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid=Reference.MODID, version=Reference.VERSION, useMetadata=true)
public class Cat_Cheats {
    @Mod.Instance(Reference.MODID)
    private static Cat_Cheats instance;

    @SidedProxy(clientSide=Reference.PROXY_CLIENT, serverSide=Reference.PROXY_SERVER)
    private static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(final FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }
    
    public static Cat_Cheats instance() {
        return Cat_Cheats.instance;
    }
}
