package org.cat73.cheats.gui;

import org.cat73.cheats.gui.main.Gui_Main;
import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.reference.Names;
import org.cat73.cheats.reference.Reference;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class Gui {
    public static final Minecraft minecraft = Minecraft.getMinecraft();

    private final KeyBinding guiKey;

    public Gui() {
        MinecraftForge.EVENT_BUS.register(this);
        this.guiKey = new KeyBinding(Names.Keys.GUI, Keyboard.KEY_F6, Reference.NAME);
        ClientRegistry.registerKeyBinding(this.guiKey);
    }

    private void enabledGui() {
        Gui.minecraft.displayGuiScreen(new Gui_Main(Mod.minecraft.currentScreen));
    }

    @SubscribeEvent
    public void keyboardEvent(final KeyInputEvent event) {
        if (Gui.minecraft.currentScreen == null) {
            if (this.guiKey.isPressed()) {
                this.enabledGui();
            }
        }
    }
}
