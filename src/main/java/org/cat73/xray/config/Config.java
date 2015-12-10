package org.cat73.xray.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import org.cat73.xray.util.PlayerMessage;

public class Config {
    private static Configuration config;
    private static int radius;
    private static int interval;
    private static int antiAntiXrayLevel;

    public static void init(File file) {
        Config.config = new Configuration(file);
        load();
        save();
    }

    public static void load() {
        Config.config.load();

        Config.radius = Config.config.get("Xray", "Radius", 45, "Radius for X-ray").getInt();
        Config.interval = Config.config.get("Xray", "Interval", 5, "Interval for X-ray(Seconds)").getInt() * 10;
        Config.antiAntiXrayLevel = Config.config.get("Xray", "AntiAntiXrayLevel", 0, "Anti Anti X-ray Level (0: off, 1~3: open)").getInt();
        
        if(Config.radius < 0) {
            PlayerMessage.warn("Radius setting error!");
            Config.radius = 45;
        }
        if(Config.interval < 0) {
            PlayerMessage.warn("Interval setting error!");
            Config.interval = 50;
        }
        if(Config.antiAntiXrayLevel > 3 || Config.antiAntiXrayLevel < 0) {
            PlayerMessage.warn("AntiAntiXrayLevel setting error!");
            Config.antiAntiXrayLevel = 0;
        }
        
        XrayBlock.load(Config.config);
    }
    
    public static void save() {
        Config.config.save();
    }

    public static int getRadius() {
        return Config.radius;
    }
    public static int getInterval() {
        return Config.interval;
    }
    public static int getAntiAntiXrayLevel() {
        return Config.antiAntiXrayLevel;
    }

    public static void setRadius(final int value) {
        Config.radius = value;
        Config.config.get("Xray", "Radius", 45, "Radius for X-ray").set(value);
    }
    public static void setInterval(final int value) {
        Config.interval = value;
        Config.config.get("Xray", "Interval", 45, "Radius for X-ray").set(value);
    }
    public static void setAntiAntiXrayLevel(final int value) {
        Config.antiAntiXrayLevel = value;
        Config.config.get("Xray", "AntiAntiXrayLevel", 45, "Radius for X-ray").set(value);
    }
}
