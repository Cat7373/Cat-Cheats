package org.cat73.cheats.config;

import java.io.File;
import java.util.HashMap;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class Config {
    private static Config instance; 
    protected final Configuration config;
    private final HashMap<String, Object> config_kv;

    public Config(final File file) {
        Config.instance = this;

        this.config = new Configuration(file);
        this.config_kv = new HashMap<String, Object>();
        load();
        save();
    }
    
    public static Config instance() {
        return Config.instance;
    }

    public void load() {
        this.config.load();

        this.config_kv.clear();
        addIntConfig("xray.radius", 45, 0, Integer.MAX_VALUE);
        addIntConfig("xray.interval", 50, 0, Integer.MAX_VALUE);
        addIntConfig("xray.antiantixraylevel", 0, 0, 1); // TODO boolean

        XrayBlock.load();
        Hotkey.load();
    }

    public void save() {
        this.config.save();
    }
    
    private void addIntConfig(String key, int defaultValue, int min, int max) {
        Property property = this.config.get("Cheats", key, defaultValue);
        int value = property.getInt();

        if(value > max || value < min) {
            value = value < min ? min : value;
            value = value > max ? max : value;
            property.set(value);
        }
        
        this.config_kv.put(key, value);
    }

    public int getIntConfig(String key) {
        return (Integer) this.config_kv.get(key);
    }
    
    public void setIntConfig(String key, int value) {
        this.config.get("Cheats", key, 0, "").set(value);
        this.config_kv.put(key, value);
    }
}
