package org.cat73.cheats.gui;

import org.cat73.cheats.gui.main.Gui_Main;
import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.reference.Reference;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class Gui {
    public final static Minecraft minecraft = Minecraft.getMinecraft();
    
    private KeyBinding guiKey;

    public Gui() {
        FMLCommonHandler.instance().bus().register(this);
        guiKey = new KeyBinding("Gui", Keyboard.KEY_F6, Reference.NAME);
        ClientRegistry.registerKeyBinding(guiKey);
    }

    @SubscribeEvent
    public void keyboardEvent(final KeyInputEvent event) {
        if (Gui.minecraft.currentScreen == null) {
            if (guiKey.isPressed()) {
                enabledGui();
            }
        }
    }

    private void enabledGui() {
        Gui.minecraft.displayGuiScreen(new Gui_Main(Mod.minecraft.currentScreen));
    }
}
