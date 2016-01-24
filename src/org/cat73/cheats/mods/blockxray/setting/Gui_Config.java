package org.cat73.cheats.mods.blockxray.setting;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import org.cat73.cheats.config.Config;
import org.cat73.cheats.gui.tool.GuiNumericField;
import org.cat73.cheats.gui.tool.GuiScreenBase;
import org.cat73.cheats.mods.ModManager;
import org.cat73.cheats.mods.blockxray.BlockXray;
import org.cat73.cheats.reference.Names;

public class Gui_Config extends GuiScreenBase {
    private final String str_save = I18n.format(Names.Mods.BlockXray.Gui.Config.SAVE);
    private final String str_cancel = I18n.format(Names.Mods.BlockXray.Gui.Config.CANCEL);
    private final String str_radius = I18n.format(Names.Mods.BlockXray.Gui.Config.RADIUS);
    private final String str_interval = I18n.format(Names.Mods.BlockXray.Gui.Config.INTERVAL);
    private final String str_antiantixray = I18n.format(Names.Mods.BlockXray.Gui.Config.ANTIANTIXRAY);
    
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

        final Config config = Config.instance();
        final int button_top = this.height - 22;
        final int button_width = this.width / 2 - 3;
        int id = 0;
        int value;

        value = this.numericRadius == null ? config.getIntConfig("blockxray.radius") : this.numericRadius.getValue();
        this.numericRadius = new GuiNumericField(this.fontRendererObj, id++, 90, 10, this.width - 95);
        this.numericRadius.setMinimum(0);
        this.numericRadius.setValue(value);
        this.buttonList.add(this.numericRadius);

        value = this.numericInterval == null ? config.getIntConfig("blockxray.interval") : this.numericInterval.getValue();
        this.numericInterval = new GuiNumericField(this.fontRendererObj, id++, 90, 30, this.width - 95);
        this.numericInterval.setMinimum(0);
        this.numericInterval.setValue(value);
        this.buttonList.add(this.numericInterval);

        value = this.numericAntiAntiXrayLevel == null ? config.getIntConfig("blockxray.antiantixraylevel") : this.numericAntiAntiXrayLevel.getValue();
        this.numericAntiAntiXrayLevel = new GuiNumericField(this.fontRendererObj, id++, 90, 50, this.width - 95);
        this.numericAntiAntiXrayLevel.setValue(value);
        this.numericAntiAntiXrayLevel.setMinimum(0);
        this.numericAntiAntiXrayLevel.setMaximum(1);
        this.buttonList.add(this.numericAntiAntiXrayLevel);

        this.btnSave = new GuiButton(id++, 3, button_top, button_width, 20, this.str_save);
        this.buttonList.add(this.btnSave);

        this.btnCancel = new GuiButton(id++, this.width / 2, button_top, button_width, 20, this.str_cancel);
        this.buttonList.add(this.btnCancel);
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground();

        drawString(this.fontRendererObj, this.str_radius, 5, 14, 0xFFFFFF);
        drawString(this.fontRendererObj, this.str_interval, 5, 34, 0xFFFFFF);
        drawString(this.fontRendererObj, this.str_antiantixray, 5, 54, 0xFFFFFF);

        super.drawScreen(par1, par2, par3);
    }

    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnSave.id) {
                final Config config = Config.instance();
                config.setIntConfig("blockxray.radius", this.numericRadius.getValue());
                config.setIntConfig("blockxray.interval", this.numericInterval.getValue());
                config.setIntConfig("blockxray.antiantixraylevel", this.numericAntiAntiXrayLevel.getValue());
                config.save();
                
                ((BlockXray) ModManager.getMod("BlockXray")).reloadConfig();
                this.mc.displayGuiScreen(this.parentScreen);
            } else if (guiButton.id == this.btnCancel.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }
}
