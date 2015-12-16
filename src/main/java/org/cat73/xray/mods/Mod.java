package org.cat73.xray.mods;

import net.minecraft.client.Minecraft;

public class Mod {
    protected final static Minecraft minecraft = Minecraft.getMinecraft();

    public final String name;
    public final String description;
    public final int hotkey;

    protected boolean enabled = false;
    
    public Mod() {
        final ModInfo info = this.getClass().getAnnotation(ModInfo.class);
        this.name = info.name();
        this.description = info.description();
        this.hotkey = info.hotkey();
    }
    
    public void toggle() {
        setEnable(!this.enabled);
    }
    
    public void setEnable(boolean enabled) {
        if(enabled) {
            onEnable();
        } else {
            onDisable();
        }

        this.enabled = enabled;
    }

    public void onEnable() {}
    public void onDisable() {}
}
