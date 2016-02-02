package org.cat73.cheats.mods;

import java.util.Collection;
import java.util.HashMap;

import org.cat73.cheats.config.Hotkey;
import org.cat73.cheats.mods.blockxray.BlockXray;
import org.cat73.cheats.mods.creategive.CreateGive;
import org.cat73.cheats.mods.freecam.FreeCam;
import org.cat73.cheats.mods.fullbright.Fullbright;
import org.cat73.cheats.reference.Reference;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public final class ModManager {
    public final static Minecraft minecraft = Minecraft.getMinecraft();

    private final static HashMap<String, Mod> mods = new HashMap<String, Mod>();
    private final static HashMap<Mod, KeyBinding> hotkeys = new HashMap<Mod, KeyBinding>();

    public static int getHotkey(final Mod mod) {
        final KeyBinding key = ModManager.hotkeys.get(mod);
        return key.getKeyCode();
    }

    public static Mod getMod(final String name) {
        return ModManager.mods.get(name);
    }

    public static Collection<Mod> getMods() {
        return ModManager.mods.values();
    }

    public static int getSize() {
        return ModManager.mods.size();
    }

    public static void setHotKey(final Mod mod, final int key) {
        final KeyBinding hotkey = ModManager.hotkeys.get(mod);
        hotkey.setKeyCode(key);
        KeyBinding.resetKeyBindingArrayAndHash();

        final HashMap<String, Integer> hotkeys = Hotkey.getHotkeys();
        hotkeys.put(mod.name, key);
        Hotkey.save();
    }

    public ModManager() {
        FMLCommonHandler.instance().bus().register(this);

        this.registerMod(new BlockXray());
        this.registerMod(new Fullbright());
        this.registerMod(new FreeCam());
        this.registerMod(new CreateGive());

        this.registerHotkeys();
        FirstTickListener.init();
    }

    @SubscribeEvent
    public void keyboardEvent(final KeyInputEvent event) {
        if (ModManager.minecraft.currentScreen == null) {
            for (final Mod mod : ModManager.hotkeys.keySet()) {
                if (ModManager.hotkeys.get(mod).isPressed()) {
                    mod.toggle();
                }
            }
        }
    }

    private void registerHotkeys() {
        final HashMap<String, Integer> hotkeys = Hotkey.getHotkeys();
        for (final String key : ModManager.mods.keySet()) {
            final Mod mod = ModManager.getMod(key);
            Integer keyCode = hotkeys.get(key);
            if (mod.shouInGui) {
                if (keyCode == null) {
                    keyCode = mod.defaultHotkey;
                }
                final KeyBinding hotkey = new KeyBinding(mod.name, keyCode, Reference.NAME);
                ModManager.hotkeys.put(mod, hotkey);
            }
        }
    }

    private void registerMod(final Mod mod) {
        ModManager.mods.put(mod.name, mod);
    }
}
