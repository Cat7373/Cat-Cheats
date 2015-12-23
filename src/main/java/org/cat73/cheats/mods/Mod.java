package org.cat73.cheats.mods;

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

    public void onEnable() {}
    public void onDisable() {}
}
