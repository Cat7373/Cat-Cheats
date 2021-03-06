package org.cat73.cheats.mods.blockxray.setting.main;

import org.cat73.cheats.config.XrayBlock;
import org.cat73.cheats.util.GuiUnit;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;

public class Gui_Main_Block_Slot extends GuiSlot {
    private static final FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.getBlockRegistry();
    private final Minecraft minecraft = Minecraft.getMinecraft();
    private final Gui_Main gui_Main;

    protected int selectedIndex = -1;

    public Gui_Main_Block_Slot(final Gui_Main gui_Main) {
        super(Minecraft.getMinecraft(), gui_Main.width, gui_Main.height, 16, gui_Main.height - 40, 24);
        this.gui_Main = gui_Main;
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawSlot(final int index, final int x, final int y, final int par4, final int mouseX, final int mouseY) {
        if ((index < 0) || (index >= this.getSize())) {
            return;
        }

        final XrayBlock xrayBlock = XrayBlock.getByIndex(index);
        final Block block = Gui_Main_Block_Slot.blockRegistery.getObjectById(xrayBlock.id);
        final String blockName = block.getLocalizedName();

        final int color = GuiUnit.colorToARGB(xrayBlock.r, xrayBlock.g, xrayBlock.b, xrayBlock.a);

        this.gui_Main.drawString(this.minecraft.fontRendererObj, blockName, x + 24, y + 6, 0x00FFFFFF);
        Gui.drawRect(x + 170, y, x + 200, y + 20, color);
    }

    @Override
    protected void elementClicked(final int slotIndex, final boolean isDoubleClick, final int mouseX, final int mouseY) {
        this.selectedIndex = slotIndex;
    }

    @Override
    protected int getSize() {
        return XrayBlock.getSize();
    }

    @Override
    protected boolean isSelected(final int slotIndex) {
        return slotIndex == this.selectedIndex;
    }

}
