package org.cat73.cheats.mods.xray.setting;

import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModSetting;
import org.cat73.cheats.mods.xray.setting.main.Gui_Main;

public class Gui implements ModSetting {
    @Override
    public void show() {
        Mod.minecraft.displayGuiScreen(new Gui_Main(Mod.minecraft.currentScreen));
    }
}
