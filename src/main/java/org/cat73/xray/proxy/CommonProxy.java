package org.cat73.xray.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public interface CommonProxy {
    void preInit(final FMLPreInitializationEvent event);
    void init(final FMLInitializationEvent event);
    void postInit(final FMLPostInitializationEvent event);
}
