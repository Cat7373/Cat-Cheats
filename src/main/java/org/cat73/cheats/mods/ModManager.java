package org.cat73.cheats.mods;

import java.util.HashMap;

import org.cat73.cheats.mods.creategive.CreateGive;
import org.cat73.cheats.mods.freecam.FreeCam;
import org.cat73.cheats.mods.fullbright.Fullbright;
import org.cat73.cheats.mods.gui.Gui;
import org.cat73.cheats.mods.xray.Xray;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public final class ModManager {
    private final static Minecraft minecraft = Minecraft.getMinecraft();
    private final static HashMap<String, Mod> mods = new HashMap<String, Mod>();
    private final static HashMap<String, KeyBinding> hotkeys = new HashMap<String, KeyBinding>();
    
    public ModManager() {
        FMLCommonHandler.instance().bus().register(this);
        
        putMod(new Gui());
        putMod(new Xray());
        putMod(new Fullbright());
        putMod(new FreeCam());
        putMod(new CreateGive());
        
        registerHotKeys();
    }
    
    public static Mod getMod(String name) {
        return mods.get(name);
    }

    private void putMod(final Mod mod) {
        mods.put(mod.name, mod);
    }
    
    // TODO 避免热键与其他 MOD 冲突
    private void registerHotKeys() {
        KeyBinding hotkey;
        for(final Mod mod : ModManager.mods.values()) {
            hotkey = new KeyBinding(mod.name, mod.hotkey, "Cat-Cheats");
            ClientRegistry.registerKeyBinding(hotkey);
            
            hotkeys.put(mod.name, hotkey);
        }
    }
    
    @SubscribeEvent
    public void keyboardEvent(final KeyInputEvent event) {
        if (ModManager.minecraft.currentScreen == null) {
            for(final Mod mod : ModManager.mods.values()) {
                if (hotkeys.get(mod.name).isPressed()) {
                    mod.toggle();
                }
            }
        }
    }
}
