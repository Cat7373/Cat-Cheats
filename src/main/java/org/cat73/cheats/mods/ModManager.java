package org.cat73.cheats.mods;

import java.util.Collection;
import java.util.HashMap;

import org.cat73.cheats.mods.creategive.CreateGive;
import org.cat73.cheats.mods.freecam.FreeCam;
import org.cat73.cheats.mods.fullbright.Fullbright;
import org.cat73.cheats.mods.xray.Xray;

import net.minecraftforge.fml.common.FMLCommonHandler;

public final class ModManager {
    private final static HashMap<String, Mod> mods = new HashMap<String, Mod>();
    
    public ModManager() {
        FMLCommonHandler.instance().bus().register(this);

        putMod(new Xray());
        putMod(new Fullbright());
        putMod(new FreeCam());
        putMod(new CreateGive());
    }

    private void putMod(final Mod mod) {
        mods.put(mod.name, mod);
    }

    public static Mod getMod(String name) {
        return mods.get(name);
    }
    
    public static Collection<Mod> getMods() {
        return mods.values();
    }
    
    public static int getSize() {
        return mods.size();
    }
}
