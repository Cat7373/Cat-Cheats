package org.cat73.cheats.mods.blockxray.setting.main;

import java.io.IOException;

import org.cat73.cheats.config.XrayBlock;
import org.cat73.cheats.gui.tool.GuiScreenBase;
import org.cat73.cheats.mods.blockxray.setting.Gui_Config;
import org.cat73.cheats.mods.blockxray.setting.addblock.Gui_AddBlock;
import org.cat73.cheats.reference.Names;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class Gui_Main extends GuiScreenBase {
    private final String str_addBlock = I18n.format(Names.Mods.BlockXray.Gui.Main.ADDBLOCK);
    private final String str_editBlock = I18n.format(Names.Mods.BlockXray.Gui.Main.EDITBLOCK);
    private final String str_deleteBlock = I18n.format(Names.Mods.BlockXray.Gui.Main.DELETEBLOCK);
    private final String str_config = I18n.format(Names.Mods.BlockXray.Gui.Main.CONFIG);
    private final String str_exit = I18n.format(Names.Mods.BlockXray.Gui.Main.EXIT);

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

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();

        this.gui_Main_Block_Slot.drawScreen(par1, par2, par3);

        if (this.gui_Main_Block_Slot.selectedIndex == -1) {
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
    public void initGui() {
        super.initGui();

        final int button_top = this.height - 22;
        final int button_width = (this.width / 5) - 2;
        int id = 0;

        this.btnAddBlock = new GuiButton(id++, 1, button_top, button_width, 20, this.str_addBlock);
        this.buttonList.add(this.btnAddBlock);

        this.btnEditBlock = new GuiButton(id++, (this.width / 5) + 1, button_top, button_width, 20, this.str_editBlock);
        this.buttonList.add(this.btnEditBlock);
        this.btnEditBlock.enabled = false;

        this.btnDeleteBlock = new GuiButton(id++, ((this.width / 5) * 2) + 1, button_top, button_width, 20, this.str_deleteBlock);
        this.buttonList.add(this.btnDeleteBlock);
        this.btnDeleteBlock.enabled = false;

        this.btnConfig = new GuiButton(id++, ((this.width / 5) * 3) + 1, button_top, button_width, 20, this.str_config);
        this.buttonList.add(this.btnConfig);

        this.btnExit = new GuiButton(id++, ((this.width / 5) * 4) + 1, button_top, button_width, 20, this.str_exit);
        this.buttonList.add(this.btnExit);

        this.gui_Main_Block_Slot = new Gui_Main_Block_Slot(this);
    }
}
