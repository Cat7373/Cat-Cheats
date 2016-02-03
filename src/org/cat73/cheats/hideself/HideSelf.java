package org.cat73.cheats.hideself;

import java.util.List;

import org.cat73.cheats.reference.Reference;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

public class HideSelf {
    /**
     * 0 = no hide
     * 1 = hide self
     * 2 = hide fml
     */
    private static final int mode = 1;

    public static void loadComplete() {
        if (HideSelf.mode == 1) {
            final List<ModContainer> mods = Loader.instance().getActiveModList();
            String modid;
            for (final ModContainer mod : mods) {
                modid = mod.getModId();
                if (modid.equals(Reference.MODID)) {
                    mods.remove(mod);
                }
            }
        }
    }
}
