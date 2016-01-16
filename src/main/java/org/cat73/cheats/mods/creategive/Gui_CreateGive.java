package org.cat73.cheats.mods.creategive;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;

import org.cat73.cheats.gui.tool.GuiNumericField;
import org.cat73.cheats.gui.tool.GuiScreenBase;

public class Gui_CreateGive extends GuiScreenBase {
    private GuiNumericField numericItemId;
    private GuiNumericField numericDamage;
    private GuiNumericField numericSlot;
    // TODO 持续按着删除键却只能删除一个字符
    private GuiTextField textNbt_Json;
    private GuiTextField textCommand;
    private GuiButton btnGive;
    private GuiButton btnGive_Command;
    private GuiButton btnCancel;

    @Override
    public void initGui() {
        super.initGui();

        final int button_top = this.height - 22;
        final int button_width = this.width / 3 - 3;
        int id = 0;
        int value;
        String value_s;

        value = this.numericSlot == null ? 1 : this.numericSlot.getValue();
        this.numericSlot = new GuiNumericField(this.fontRendererObj, id++, 90, 10, this.width - 95);
        this.numericSlot.setValue(value);
        this.numericSlot.setMinimum(0);
        this.numericSlot.setMaximum(9);
        this.buttonList.add(this.numericSlot);

        value = this.numericItemId == null ? 1 : this.numericItemId.getValue();
        this.numericItemId = new GuiNumericField(this.fontRendererObj, id++, 90, 40, this.width - 95);
        this.numericItemId.setMinimum(0);
        this.numericItemId.setValue(value);
        this.buttonList.add(this.numericItemId);

        value = this.numericDamage == null ? 0 : this.numericDamage.getValue();
        this.numericDamage = new GuiNumericField(this.fontRendererObj, id++, 90, 60, this.width - 95);
        this.numericDamage.setMinimum(0);
        this.numericDamage.setMaximum(32767);
        this.numericDamage.setValue(value);
        this.buttonList.add(this.numericDamage);
        
        value_s = this.textNbt_Json == null ? "{}" : this.textNbt_Json.getText();
        this.textNbt_Json = new GuiTextField(this.fontRendererObj, 90, 80, this.width - 100, 20);
        this.textNbt_Json.setMaxStringLength(Integer.MAX_VALUE);
        this.textNbt_Json.setText(value_s);
        this.textFields.add(this.textNbt_Json);

        value_s = this.textCommand == null ? "/give Cat73 minecraft:stone 64 0 {display:{Name:\"Cat73 stone\"}}" : this.textCommand.getText();
        this.textCommand = new GuiTextField(this.fontRendererObj, 90, 110, this.width - 100, 20);
        this.textCommand.setMaxStringLength(Integer.MAX_VALUE);
        this.textCommand.setText(value_s);
        this.textFields.add(this.textCommand);
        
        this.btnGive = new GuiButton(id++, 3, button_top, button_width, 20, "Give");
        this.buttonList.add(this.btnGive);
        
        this.btnGive_Command = new GuiButton(id++, this.width / 3, button_top, button_width, 20, "Give_Command");
        this.buttonList.add(this.btnGive_Command);

        this.btnCancel = new GuiButton(id++, this.width / 3 * 2, button_top, button_width, 20, "Exit");
        this.buttonList.add(this.btnCancel);
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground();
        
        super.drawScreen(par1, par2, par3);

        // TODO 尝试利用super的labelList
        drawString(this.fontRendererObj, "Slot:", 5, 14, 0xFFFFFF);
        drawString(this.fontRendererObj, "ItemId:", 5, 44, 0xFFFFFF);
        drawString(this.fontRendererObj, "Damage:", 5, 64, 0xFFFFFF);
        drawString(this.fontRendererObj, "NBT_Json:", 5, 84, 0xFFFFFF);
        drawString(this.fontRendererObj, "Give Command:", 5, 114, 0xFFFFFF);
    }

    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnGive.id) {
                // TODO 增加数量设置
                CreateGive.giveItem(this.numericItemId.getValue(), this.numericDamage.getValue(), this.numericSlot.getValue(), 1, this.textNbt_Json.getText());
                this.mc.displayGuiScreen(this.parentScreen);
            } else if (guiButton.id == this.btnGive_Command.id) {
                CreateGive.giveItem(textCommand.getText(), this.numericSlot.getValue());
                this.mc.displayGuiScreen(this.parentScreen);
            } else if (guiButton.id == this.btnCancel.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }
}
