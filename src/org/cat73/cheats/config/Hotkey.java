package org.cat73.cheats.config;

import java.util.HashMap;

import net.minecraftforge.common.config.Property;

public class Hotkey {
    private final static HashMap<String, Integer> hotkeys = new HashMap<String, Integer>();

    public static HashMap<String, Integer> getHotkeys() {
        return Hotkey.hotkeys;
    }

    public static void load() {
        final String[] configHotkeyList = Config.instance().config.get("Cheats", "mods.hotkeys", new String[0], "").getStringList();
        for (final String hotkey : configHotkeyList) {
            final String[] hotkeyInfo = hotkey.split(" ");
            Hotkey.hotkeys.put(hotkeyInfo[0], Integer.valueOf(hotkeyInfo[1]));
        }
    }

    public static void save() {
        final Property configBolcks = Config.instance().config.get("Cheats", "mods.hotkeys", new String[0], "");
        final String[] configHotkeyList = new String[Hotkey.hotkeys.size()];
        int i = 0;
        for (final String key : Hotkey.hotkeys.keySet()) {
            configHotkeyList[i++] = key + " " + Hotkey.hotkeys.get(key);
        }
        configBolcks.set(configHotkeyList);
        Config.instance().save();
    }
}
