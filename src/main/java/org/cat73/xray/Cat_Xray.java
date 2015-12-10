package org.cat73.xray;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.cat73.xray.proxy.CommonProxy;
import org.cat73.xray.reference.Reference;

@Mod(modid = Reference.MODID, version = Reference.VERSION)
public class Cat_Xray {
    @SidedProxy(serverSide=Reference.PROXY_SERVER, clientSide = Reference.PROXY_CLIENT)
    private static CommonProxy proxy;

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
