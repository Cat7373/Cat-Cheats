package org.cat73.xray.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.github.lunatrius.core.client.gui.GuiScreenBase;

public class Gui_Main extends GuiScreenBase {
    // TODO 透视的方块编辑
    private GuiButton btnAddBlock = null;
    private GuiButton btnEditBlock = null;
    private GuiButton btnDeleteBlock = null;
    private GuiButton btnConfig = null;
    private GuiButton btnExit = null;

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
    }
    
    @Override
    public void drawScreen(int par1, int par2, float par3) {
        drawDefaultBackground();

        super.drawScreen(par1, par2, par3);
    }
    
    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnConfig.id) {
                this.mc.displayGuiScreen(new Gui_Config(this));
            }
            if (guiButton.id == this.btnExit.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }
}
