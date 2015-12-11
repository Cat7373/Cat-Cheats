package org.cat73.xray.gui.main;

import java.io.IOException;

import org.cat73.xray.gui.Gui_Config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.github.lunatrius.core.client.gui.GuiScreenBase;

public class Gui_Main extends GuiScreenBase {
    private Gui_Main_Block_Slot gui_Main_Block_Slot;
    private GuiButton btnAddBlock;
    private GuiButton btnEditBlock;
    private GuiButton btnDeleteBlock;
    private GuiButton btnConfig;
    private GuiButton btnExit;

    public Gui_Main(GuiScreen guiScreen) {
        super(guiScreen);
    }
    
    @Override
    public void initGui() {
        this.buttonList.clear();
        
        final int button_top = this.height - 22;
        final int button_width = this.width / 11 * 2 - 3;
        int id = 0;

        // TODO 按钮位置对齐
        this.btnAddBlock = new GuiButton(id++, this.width / 11 * 1, button_top, button_width, 20, "Add Block");
        this.buttonList.add(this.btnAddBlock);
        this.btnAddBlock.enabled = false;

        this.btnEditBlock = new GuiButton(id++, this.width / 11 * 3, button_top, button_width, 20, "Edit Block");
        this.buttonList.add(this.btnEditBlock);
        this.btnEditBlock.enabled = false;

        this.btnDeleteBlock = new GuiButton(id++, this.width / 11 * 5, button_top, button_width, 20, "Del Block");
        this.buttonList.add(this.btnDeleteBlock);
        this.btnDeleteBlock.enabled = false;

        this.btnConfig = new GuiButton(id++, this.width / 11 * 7, button_top, button_width, 20, "Config");
        this.buttonList.add(this.btnConfig);

        this.btnExit = new GuiButton(id++, this.width / 11 * 9, button_top, button_width, 20, "Exit");
        this.buttonList.add(this.btnExit);
        
        this.gui_Main_Block_Slot = new Gui_Main_Block_Slot(this);
    }
    
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        drawDefaultBackground();
        
        this.gui_Main_Block_Slot.drawScreen(par1, par2, par3);

        super.drawScreen(par1, par2, par3);
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.gui_Main_Block_Slot.handleMouseInput();
    }
    
    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnConfig.id) {
                this.mc.displayGuiScreen(new Gui_Config(this));
            } else if (guiButton.id == this.btnExit.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            } else {
                this.gui_Main_Block_Slot.actionPerformed(guiButton);
            }
        }
    }
}
