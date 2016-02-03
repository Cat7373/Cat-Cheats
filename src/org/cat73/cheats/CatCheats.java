package org.cat73.cheats;

import org.cat73.cheats.proxy.ClientProxy;
import org.cat73.cheats.proxy.CommonProxy;
import org.cat73.cheats.proxy.ServerProxy;

import com.google.common.eventbus.Subscribe;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class CatCheats extends ModContainer {
    private static CommonProxy proxy;

    @Subscribe
    public void init(final FMLInitializationEvent event) {
        CatCheats.proxy.init(event);
    }

    @Subscribe
    public void loadComplete(final FMLLoadCompleteEvent event) {
        CatCheats.proxy.loadComplete(event);
    }

    @Subscribe
    public void postInit(final FMLPostInitializationEvent event) {
        CatCheats.proxy.postInit(event);
    }

    @Subscribe
    public void preInit(final FMLPreInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            CatCheats.proxy = new ClientProxy();
        } else {
            CatCheats.proxy = new ServerProxy();
        }

        CatCheats.proxy.preInit(event);
    }
}
