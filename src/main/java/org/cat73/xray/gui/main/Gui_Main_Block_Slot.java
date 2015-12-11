package org.cat73.xray.gui.main;

import org.cat73.xray.config.XrayBlock;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;

public class Gui_Main_Block_Slot extends GuiSlot {
    private final Minecraft minecraft = Minecraft.getMinecraft();
    private static final FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.getBlockRegistry();
    private final Gui_Main gui_Main;
    
    protected int selectedIndex = -1;

    public Gui_Main_Block_Slot(Gui_Main gui_Main) {
        super(Minecraft.getMinecraft(), gui_Main.width, gui_Main.height, 16, gui_Main.height - 40, 24);
        this.gui_Main = gui_Main;
    }

    @Override
    protected int getSize() {
        return XrayBlock.getSize();
    }

    @Override
    protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
        this.selectedIndex = slotIndex;
    }

    @Override
    protected boolean isSelected(int slotIndex) {
        return slotIndex == this.selectedIndex;
    }

    @Override
    protected void drawBackground() {
    }

    @Override
    protected void drawSlot(int index, int x, int y, int par4, int mouseX, int mouseY) {
        if (index < 0 || index >= getSize()) {
            return;
        }

        final XrayBlock xrayBlock = XrayBlock.getByIndex(index);
        final Block block = blockRegistery.getObjectById(xrayBlock.id);
        final String blockName = block.getLocalizedName();
        // TODO 颜色计算挪到 unit 中
        final int color = ((xrayBlock.a << 24) & 0xff000000) | ((xrayBlock.r << 16) & 0x00ff0000) | ((xrayBlock.g << 8) & 0x0000ff00) | (xrayBlock.b & 0x000000ff);

        gui_Main.drawString(this.minecraft.fontRendererObj, blockName, x + 24, y + 6, 0x00FFFFFF);
        Gui.drawRect(x + 180, y, x + 200, y + 20, color);
    }

}
