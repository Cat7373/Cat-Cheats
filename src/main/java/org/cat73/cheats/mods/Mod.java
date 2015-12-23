package org.cat73.cheats.mods;

import net.minecraft.client.Minecraft;

public class Mod {
    public final static Minecraft minecraft = Minecraft.getMinecraft();

    public final String name;
    public final String description;
    public final boolean shouInGui;
    public final String settingClassName;
    public final int defaultHotkey;

    protected boolean enabled = false;
    
    public Mod() {
        final ModInfo info = this.getClass().getAnnotation(ModInfo.class);
        this.name = info.name();
        this.description = info.description();
        this.shouInGui = info.showInGui();
        this.settingClassName = info.settingClassName();
        this.defaultHotkey = info.defaultHotkey();
    }
    
    public void toggle() {
        setEnabled(!this.enabled);
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;

        if(this.enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }

    public void onEnable() {}
    public void onDisable() {}
}
