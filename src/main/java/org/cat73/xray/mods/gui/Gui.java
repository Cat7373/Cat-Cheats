package org.cat73.xray.mods.gui;

import org.cat73.xray.mods.Mod;
import org.cat73.xray.mods.ModInfo;
import org.cat73.xray.mods.gui.main.Gui_Main;
import org.lwjgl.input.Keyboard;

@ModInfo(name="Toggle Setting", hotkey=Keyboard.KEY_F6)
public class Gui extends Mod {
    @Override
    public void onEnable() {
        Mod.minecraft.displayGuiScreen(new Gui_Main(Mod.minecraft.currentScreen));
    }
}
