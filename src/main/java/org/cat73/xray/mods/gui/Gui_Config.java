package org.cat73.xray.mods.gui;

import org.cat73.xray.config.Config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.github.lunatrius.core.client.gui.GuiNumericField;
import com.github.lunatrius.core.client.gui.GuiScreenBase;

public class Gui_Config extends GuiScreenBase {
    private GuiNumericField numericRadius;
    private GuiNumericField numericInterval;
    private GuiNumericField numericAntiAntiXrayLevel;
    private GuiButton btnSave;
    private GuiButton btnCancel;

    public Gui_Config(final GuiScreen guiScreen) {
        super(guiScreen);
    }

    @Override
    public void initGui() {
        super.initGui();

        final int button_top = this.height - 22;
        final int button_width = this.width / 2 - 3;
        int id = 0;
        int value;

        value = this.numericRadius == null ? Config.getRadius() : this.numericRadius.getValue();
        this.numericRadius = new GuiNumericField(this.fontRendererObj, id++, 90, 10, this.width - 95);
        this.numericRadius.setMinimum(0);
        this.numericRadius.setValue(value);
        this.buttonList.add(this.numericRadius);

        value = this.numericInterval == null ? Config.getInterval() : this.numericInterval.getValue();
        this.numericInterval = new GuiNumericField(this.fontRendererObj, id++, 90, 30, this.width - 95);
        this.numericInterval.setMinimum(0);
        this.numericInterval.setValue(value);
        this.buttonList.add(this.numericInterval);

        value = this.numericAntiAntiXrayLevel == null ? Config.getAntiAntiXrayLevel() : this.numericAntiAntiXrayLevel.getValue();
        this.numericAntiAntiXrayLevel = new GuiNumericField(this.fontRendererObj, id++, 90, 50, this.width - 95);
        this.numericAntiAntiXrayLevel.setValue(value);
        this.numericAntiAntiXrayLevel.setMinimum(0);
        this.numericAntiAntiXrayLevel.setMaximum(3);
        this.buttonList.add(this.numericAntiAntiXrayLevel);

        this.btnSave = new GuiButton(id++, 3, button_top, button_width, 20, "Save");
        this.buttonList.add(this.btnSave);

        this.btnCancel = new GuiButton(id++, this.width / 2, button_top, button_width, 20, "Cancel");
        this.buttonList.add(this.btnCancel);
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground();

        drawString(this.fontRendererObj, "Radius:", 5, 14, 0xFFFFFF);
        drawString(this.fontRendererObj, "Interval:", 5, 34, 0xFFFFFF);
        drawString(this.fontRendererObj, "AntiAntiXrayLevel:", 5, 54, 0xFFFFFF);

        super.drawScreen(par1, par2, par3);
    }

    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnSave.id) {
                Config.setRadius(this.numericRadius.getValue());
                Config.setInterval(this.numericInterval.getValue());
                Config.setAntiAntiXrayLevel(this.numericAntiAntiXrayLevel.getValue());
                Config.save();
                this.mc.displayGuiScreen(this.parentScreen);
            } else if (guiButton.id == this.btnCancel.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }
}