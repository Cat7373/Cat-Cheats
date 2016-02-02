package org.cat73.cheats.gui.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import org.cat73.cheats.gui.Gui;
import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModManager;
import org.cat73.cheats.reference.Names;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;

public class Gui_Main_Mods_Slot extends GuiSlot {
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private final String str_enable = I18n.format(Names.Gui.ENABLE);

    private final String str_disable = I18n.format(Names.Gui.DISABLE);
    private final Gui_Main gui_Main;
    protected final Mod[] mods;

    protected int selectedIndex = -1;

    public Gui_Main_Mods_Slot(final Gui_Main gui_Main) {
        super(Gui.minecraft, gui_Main.width, gui_Main.height, 16, gui_Main.height - 40, 24);
        this.gui_Main = gui_Main;

        final ArrayList<Mod> mods = new ArrayList<Mod>(ModManager.getMods());
        final Iterator<Mod> it = mods.iterator();
        while (it.hasNext()) {
            if (!it.next().shouInGui) {
                it.remove();
            }
        }
        this.mods = mods.toArray(new Mod[mods.size()]);
        Arrays.sort(this.mods, new Comparator<Mod>() {
            @Override
            public int compare(final Mod mod1, final Mod mod2) {
                return mod1.name.compareToIgnoreCase(mod2.name);
            }
        });
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawSlot(final int index, final int x, final int y, final int par4, final Tessellator tessellator, final int mouseX, final int mouseY) {
        if ((index < 0) || (index >= this.getSize())) {
            return;
        }

        final Mod mod = this.mods[index];
        final String modName = I18n.format("catcheats.mods." + mod.name.toLowerCase() + ".name");
        final String enable = mod.isEnabled() ? this.str_enable : this.str_disable;
        final int keyCode = ModManager.getHotkey(mod);

        this.gui_Main.drawString(Gui_Main_Mods_Slot.minecraft.fontRenderer, modName, x + 24, y + 6, 0x00FFFFFF);
        this.gui_Main.drawString(Gui_Main_Mods_Slot.minecraft.fontRenderer, enable, x + 120, y + 6, 0x00FFFFFF);
        if (keyCode != Keyboard.KEY_NONE) {
            this.gui_Main.drawString(Gui_Main_Mods_Slot.minecraft.fontRenderer, Keyboard.getKeyName(keyCode), x + 180, y + 6, 0x00FFFFFF);
        }
    }

    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        this.selectedIndex = slotIndex;
    }

    protected Mod getSelectMod() {
        return this.mods[this.selectedIndex];
    }

    @Override
    protected int getSize() {
        return this.mods.length;
    }

    @Override
    protected boolean isSelected(final int slotIndex) {
        return slotIndex == this.selectedIndex;
    }
}
