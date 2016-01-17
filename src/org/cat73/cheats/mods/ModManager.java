package org.cat73.cheats.mods;

import java.util.Collection;
import java.util.HashMap;

import org.cat73.cheats.config.Hotkey;
import org.cat73.cheats.mods.creategive.CreateGive;
import org.cat73.cheats.mods.freecam.FreeCam;
import org.cat73.cheats.mods.fullbright.Fullbright;
import org.cat73.cheats.mods.xray.Xray;
import org.cat73.cheats.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public final class ModManager {
    public final static Minecraft minecraft = Minecraft.getMinecraft();

    private final static HashMap<String, Mod> mods = new HashMap<String, Mod>();
    private final static HashMap<Mod, KeyBinding> hotkeys = new HashMap<Mod, KeyBinding>();
    
    public ModManager() {
        FMLCommonHandler.instance().bus().register(this);

        putMod(new Xray());
        putMod(new Fullbright());
        putMod(new FreeCam());
        putMod(new CreateGive());
        
        registerHotkeys();
    }

    private void registerHotkeys() {
        final HashMap<String, Integer> hotkeys = Hotkey.getHotkeys();
        for(String key : ModManager.mods.keySet()) {
            final Mod mod = getMod(key);
            Integer keyCode = hotkeys.get(key);
            if(mod.shouInGui) {
                if(keyCode == null) {
                    keyCode = mod.defaultHotkey;
                }
                final KeyBinding hotkey = new KeyBinding(mod.name, keyCode, Reference.NAME);
                ModManager.hotkeys.put(mod, hotkey);
            }
        }
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
    
    public static void setHotKey(Mod mod, int key) {
        final KeyBinding hotkey = ModManager.hotkeys.get(mod);
        hotkey.setKeyCode(key);
        KeyBinding.resetKeyBindingArrayAndHash();
        
        final HashMap<String, Integer> hotkeys = Hotkey.getHotkeys();
        hotkeys.put(mod.name, key);
        Hotkey.save();
    }
    
    public static int getHotkey(Mod mod) {
        KeyBinding key = ModManager.hotkeys.get(mod);
        return key.getKeyCode();
    }
    
    @SubscribeEvent
    public void keyboardEvent(final KeyInputEvent event) {
        if (ModManager.minecraft.currentScreen == null) {
            for(Mod mod : hotkeys.keySet()) {
                if(ModManager.hotkeys.get(mod).isPressed()) {
                    mod.toggle();
                }
            }
        }
    }
}
