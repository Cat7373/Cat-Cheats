package org.cat73.cheats.config;

import java.io.File;
import java.util.HashMap;

import net.minecraftforge.common.config.Configuration;

public class Config {
    private static Config instance;

    public static Config instance() {
        return Config.instance;
    }

    protected final Configuration config;

    private final HashMap<String, Object> config_kv;

    public Config(final File file) {
        Config.instance = this;

        this.config = new Configuration(file);
        this.config_kv = new HashMap<String, Object>();
        this.load();
        this.save();
    }

    private void addIntConfig(final String key, final int defaultValue, final int min, final int max) {
        final int value = this.config.get("Cheats", key, defaultValue, "", min, max).getInt();
        this.config_kv.put(key, value);
    }

    public int getIntConfig(final String key) {
        return (Integer) this.config_kv.get(key);
    }

    public void load() {
        this.config.load();

        this.config_kv.clear();
        this.addIntConfig("blockxray.radius", 45, 0, Integer.MAX_VALUE);
        this.addIntConfig("blockxray.interval", 50, 0, Integer.MAX_VALUE);
        this.addIntConfig("blockxray.antiantixraylevel", 0, 0, 1); // TODO boolean

        XrayBlock.load();
        Hotkey.load();
    }

    public void save() {
        this.config.save();
    }

    public void setIntConfig(final String key, final int value) {
        this.config.get("Cheats", key, 0, "").set(value);
        this.config_kv.put(key, value);
    }
}
