package org.cat73.cheats.mods.creategive;

import org.cat73.cheats.gui.tool.GuiNumericField;
import org.cat73.cheats.gui.tool.GuiScreenBase;
import org.cat73.cheats.reference.Names;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;

public class Gui_CreateGive extends GuiScreenBase {
    private final String str_execute = I18n.format(Names.Mods.CreateGive.Gui.EXECUTE);
    private final String str_exit = I18n.format(Names.Mods.CreateGive.Gui.EXIT);
    private final String str_slot = I18n.format(Names.Mods.CreateGive.Gui.SLOT);
    private final String str_command = I18n.format(Names.Mods.CreateGive.Gui.COMMAND);
    
    private GuiNumericField numericSlot;
    private GuiTextField textCommand;
    private GuiButton btnExecute;
    private GuiButton btnExit;

    @Override
    public void initGui() {
        super.initGui();

        final int button_top = this.height - 22;
        final int button_width = this.width / 2 - 2;
        int id = 0;
        int value;
        String value_s;

        value = this.numericSlot == null ? 1 : this.numericSlot.getValue();
        this.numericSlot = new GuiNumericField(this.fontRendererObj, id++, 90, 10, this.width - 95);
        this.numericSlot.setValue(value);
        this.numericSlot.setMinimum(0);
        this.numericSlot.setMaximum(9);
        this.buttonList.add(this.numericSlot);

        value_s = this.textCommand == null ? "/give Cat73 minecraft:stone 64 0 {display:{Name:\"Cat73 stone\"}}" : this.textCommand.getText();
        this.textCommand = new GuiTextField(this.fontRendererObj, 90, 35, this.width - 100, 20);
        this.textCommand.setMaxStringLength(Integer.MAX_VALUE);
        this.textCommand.setText(value_s);
        this.textFields.add(this.textCommand);
        
        this.btnExecute = new GuiButton(id++, 1, button_top, button_width, 20, this.str_execute);
        this.buttonList.add(this.btnExecute);

        this.btnExit = new GuiButton(id++, this.width / 2 + 1, button_top, button_width, 20, this.str_exit);
        this.buttonList.add(this.btnExit);
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground();
        
        super.drawScreen(par1, par2, par3);

        drawString(this.fontRendererObj, this.str_slot, 5, 14, 0xFFFFFF);
        drawString(this.fontRendererObj, this.str_command, 5, 39, 0xFFFFFF);
    }

    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnExecute.id) {
                CreateGive.giveItem(textCommand.getText(), this.numericSlot.getValue());
                this.mc.displayGuiScreen(this.parentScreen);
            } else if (guiButton.id == this.btnExit.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }
}
