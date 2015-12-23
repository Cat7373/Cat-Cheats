package org.cat73.cheats.mods.gui;

import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModInfo;
import org.cat73.cheats.mods.gui.main.Gui_Main;

@ModInfo(name="Toggle Setting")
public class Gui extends Mod {
    @Override
    public void onEnable() {
        Mod.minecraft.displayGuiScreen(new Gui_Main(Mod.minecraft.currentScreen));
        this.setEnabled(false);
    }
}
