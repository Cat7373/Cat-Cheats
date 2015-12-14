package org.cat73.xray.mods;

import net.minecraft.client.Minecraft;

public class Mod {
    protected final static Minecraft minecraft = Minecraft.getMinecraft();

    public final String name;
    public final String description;
    public final int hotkey;

    protected boolean enabled;
    
    public Mod() {
        onInit();
        
        final ModInfo info = this.getClass().getAnnotation(ModInfo.class);
        this.name = info.name();
        this.description = info.description();
        this.hotkey = info.hotkey();

        this.enabled = false;
    }
    
    public void toggle() {
        if(this.enabled) {
            onDisable();
        } else {
            onEnable();
        }
        enabled = !enabled;
    }
    
    public void onInit() {}
    public void onEnable() {}
    public void onDisable() {}
}
