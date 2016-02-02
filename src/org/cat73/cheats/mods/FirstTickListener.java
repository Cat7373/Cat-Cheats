package org.cat73.cheats.mods;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;

public class FirstTickListener {
    private static FirstTickListener self;

    public static void init() {
        FirstTickListener.self = new FirstTickListener();
        FMLCommonHandler.instance().bus().register(FirstTickListener.self);
    }

    @SubscribeEvent
    public void onTickInGame(final ClientTickEvent event) {
        FMLCommonHandler.instance().bus().unregister(FirstTickListener.self);

        // 执行每一个 Mod 的 onFirstTick
        for (final Mod mod : ModManager.getMods()) {
            mod.onFirstTick();
        }

        FirstTickListener.self = null;
    }
}
