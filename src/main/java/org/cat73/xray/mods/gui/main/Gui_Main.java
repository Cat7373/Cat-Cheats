package org.cat73.xray.mods.gui.main;

import java.io.IOException;

import org.cat73.xray.config.XrayBlock;
import org.cat73.xray.mods.gui.Gui_Config;
import org.cat73.xray.mods.gui.addblock.Gui_AddBlock;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.github.lunatrius.core.client.gui.GuiScreenBase;

public class Gui_Main extends GuiScreenBase {
    // TODO 增加方块顺序调整的功能
    private Gui_Main_Block_Slot gui_Main_Block_Slot;
    private GuiButton btnAddBlock;
    private GuiButton btnEditBlock;
    private GuiButton btnDeleteBlock;
    private GuiButton btnConfig;
    private GuiButton btnExit;

    public Gui_Main(final GuiScreen guiScreen) {
        super(guiScreen);
    }

    @Override
    public void initGui() {
        super.initGui();

        final int button_top = this.height - 22;
        final int button_width = this.width / 10 * 2 - 2;
        int id = 0;

        this.btnAddBlock = new GuiButton(id++, 1, button_top, button_width, 20, "Add Block");
        this.buttonList.add(this.btnAddBlock);

        this.btnEditBlock = new GuiButton(id++, this.width / 10 * 2 + 1, button_top, button_width, 20, "Edit Block");
        this.buttonList.add(this.btnEditBlock);
        this.btnEditBlock.enabled = false;

        this.btnDeleteBlock = new GuiButton(id++, this.width / 10 * 4 + 1, button_top, button_width, 20, "Del Block");
        this.buttonList.add(this.btnDeleteBlock);
        this.btnDeleteBlock.enabled = false;

        this.btnConfig = new GuiButton(id++, this.width / 10 * 6 + 1, button_top, button_width, 20, "Config");
        this.buttonList.add(this.btnConfig);

        this.btnExit = new GuiButton(id++, this.width / 10 * 8 + 1, button_top, button_width, 20, "Exit");
        this.buttonList.add(this.btnExit);

        this.gui_Main_Block_Slot = new Gui_Main_Block_Slot(this);
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground();

        this.gui_Main_Block_Slot.drawScreen(par1, par2, par3);

        if(this.gui_Main_Block_Slot.selectedIndex == -1) {
            this.btnEditBlock.enabled = false;
            this.btnDeleteBlock.enabled = false;
        } else {
            this.btnEditBlock.enabled = true;
            this.btnDeleteBlock.enabled = true;
        }

        super.drawScreen(par1, par2, par3);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.gui_Main_Block_Slot.handleMouseInput();
    }

    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnAddBlock.id) {
                this.mc.displayGuiScreen(new Gui_AddBlock(this));
            } else if (guiButton.id == this.btnEditBlock.id) {
                this.mc.displayGuiScreen(new Gui_AddBlock(this, this.gui_Main_Block_Slot.selectedIndex));
            } else if (guiButton.id == this.btnDeleteBlock.id) {
                XrayBlock.delByIndex(this.gui_Main_Block_Slot.selectedIndex);
                XrayBlock.save();
            } else if (guiButton.id == this.btnConfig.id) {
                this.mc.displayGuiScreen(new Gui_Config(this));
            } else if (guiButton.id == this.btnExit.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            } else {
                this.gui_Main_Block_Slot.actionPerformed(guiButton);
            }
        }
    }
}
