package org.cat73.cheats.gui.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiSlot;

public class Gui_Main_Mods_Slot extends GuiSlot {
    private static final Minecraft minecraft = Minecraft.getMinecraft();
    private final Gui_Main gui_Main;
    protected final Mod[] mods;

    protected int selectedIndex = -1;

    public Gui_Main_Mods_Slot(final Gui_Main gui_Main) {
        super(Minecraft.getMinecraft(), gui_Main.width, gui_Main.height, 16, gui_Main.height - 40, 24);
        this.gui_Main = gui_Main;

        ArrayList<Mod> mods = new ArrayList<Mod>(ModManager.getMods());
        Iterator<Mod> it = mods.iterator();
        while(it.hasNext()) {
            if(!it.next().shouInGui) {
                it.remove();
            }
        }
        this.mods = mods.toArray(new Mod[0]);
        Arrays.sort(this.mods, new Comparator<Mod>() {
            @Override
            public int compare(Mod mod1, Mod mod2) {
                return mod1.name.compareTo(mod2.name);
            }
        });
    }

    @Override
    protected int getSize() {
        return this.mods.length;
    }

    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        this.selectedIndex = slotIndex;
    }

    @Override
    protected boolean isSelected(final int slotIndex) {
        return slotIndex == this.selectedIndex;
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawSlot(final int index, final int x, final int y, final int par4, final int mouseX, final int mouseY) {
        if (index < 0 || index >= getSize()) {
            return;
        }

        final Mod mod = mods[index];
        final String modName = mod.name;
        final String enable = mod.isEnabled() ? "enable" : "disable";

        gui_Main.drawString(Gui_Main_Mods_Slot.minecraft.fontRendererObj, modName, x + 24, y + 6, 0x00FFFFFF);
        gui_Main.drawString(Gui_Main_Mods_Slot.minecraft.fontRendererObj, enable, x + 160, y + 6, 0x00FFFFFF);
    }
    
    protected Mod getSelectMod() {
        return this.mods[this.selectedIndex];
    }
}
