package org.cat73.xray.gui;

import org.cat73.xray.gui.main.Gui_Main;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class Gui {
    private final Minecraft minecraft;

    private final KeyBinding toggleXrayGui;

    private Gui() {
        this.minecraft = Minecraft.getMinecraft();

        this.toggleXrayGui = new KeyBinding("Toggle Setting", Keyboard.KEY_F6, "Cat-Xray");
        ClientRegistry.registerKeyBinding(this.toggleXrayGui);

        FMLCommonHandler.instance().bus().register(this);
    }

    public static void init() {
        new Gui();
    }

    @SubscribeEvent
    public void keyboardEvent(final KeyInputEvent event) {
        if (this.minecraft.currentScreen == null) {
            if (this.toggleXrayGui.isPressed()) {
                this.minecraft.displayGuiScreen(new Gui_Main(this.minecraft.currentScreen));
            }
        }
    }
}
