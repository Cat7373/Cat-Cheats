package org.cat73.cheats.mods;

import net.minecraft.client.Minecraft;

public class Mod {
    public static final Minecraft minecraft = Minecraft.getMinecraft();

    public final String name;
    public final String description;
    public final boolean shouInGui;
    protected ModSetting settingInstance = null;
    public final int defaultHotkey;

    protected boolean enabled = false;

    public Mod() {
        final ModInfo info = this.getClass().getAnnotation(ModInfo.class);
        this.name = info.name();
        this.description = info.description();
        this.shouInGui = info.showInGui();
        this.defaultHotkey = info.defaultHotkey();
    }

    public ModSetting getSettingInstance() {
        return this.settingInstance;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void onDisable() {
    }

    public void onEnable() {
    }

    public void onFirstTick() {
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;

        if (this.enabled) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public void toggle() {
        this.setEnabled(!this.enabled);
    }
}
