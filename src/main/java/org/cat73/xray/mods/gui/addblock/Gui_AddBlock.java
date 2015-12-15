package org.cat73.xray.mods.gui.addblock;

import org.cat73.xray.config.XrayBlock;
import org.cat73.xray.util.GuiUnit;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import com.github.lunatrius.core.client.gui.GuiNumericField;
import com.github.lunatrius.core.client.gui.GuiScreenBase;

public class Gui_AddBlock extends GuiScreenBase {
    private GuiNumericField numericBlockId;
    private GuiNumericField numericMeta;
    private GuiSlider slider_r;
    private GuiSlider slider_g;
    private GuiSlider slider_b;
    private GuiSlider slider_a;
    private GuiButton btnSave;
    private GuiButton btnCancel;

    private int xrayBlockIndex = -1;
    private XrayBlock xrayBlock = null;

    public Gui_AddBlock(final GuiScreen guiScreen) {
        super(guiScreen);
    }

    public Gui_AddBlock(final GuiScreen guiScreen, final int xrayBlockIndex) {
        this(guiScreen);
        this.xrayBlockIndex = xrayBlockIndex;
        this.xrayBlock = XrayBlock.getByIndex(xrayBlockIndex);
    }

    @Override
    public void initGui() {
        this.buttonList.clear();

        final int button_top = this.height - 22;
        final int button_width = this.width / 2 - 3;
        int id = 0;
        int value;

        value = this.numericBlockId == null ? this.xrayBlock == null ? 0 : this.xrayBlock.id : this.numericBlockId.getValue();
        this.numericBlockId = new GuiNumericField(this.fontRendererObj, id++, 90, 10, (int) (this.width / 3));
        this.numericBlockId.setMinimum(0);
        this.numericBlockId.setValue(value);
        this.buttonList.add(this.numericBlockId);

        value = this.numericMeta == null ? this.xrayBlock == null ? 0 : this.xrayBlock.meta : this.numericMeta.getValue();
        this.numericMeta = new GuiNumericField(this.fontRendererObj, id++, 90, 30, (int) (this.width / 3));
        this.numericMeta.setMinimum(-1);
        this.numericMeta.setMaximum(15);
        this.numericMeta.setValue(value);
        this.buttonList.add(this.numericMeta);

        this.slider_r = new GuiSlider(id++, this.width - 160, this.height / 10 * 5, "Red-Value", this.slider_r == null ? this.xrayBlock == null ? 0.5f : (this.xrayBlock.r & 255) / 255.0f : this.slider_r.percent);
        this.buttonList.add(this.slider_r);
        this.slider_g = new GuiSlider(id++, this.width - 160, this.height / 10 * 6, "Green-Value", this.slider_g == null ? this.xrayBlock == null ? 0.5f : (this.xrayBlock.g & 255) / 255.0f : this.slider_g.percent);
        this.buttonList.add(this.slider_g);
        this.slider_b = new GuiSlider(id++, this.width - 160, this.height / 10 * 7, "Blue-Value", this.slider_b == null ? this.xrayBlock == null ? 0.5f : (this.xrayBlock.b & 255) / 255.0f : this.slider_b.percent);
        this.buttonList.add(this.slider_b);
        this.slider_a = new GuiSlider(id++, this.width - 160, this.height / 10 * 8, "Alpha-Value", this.slider_a == null ? this.xrayBlock == null ? 1.0f : (this.xrayBlock.a & 255) / 255.0f : this.slider_a.percent);
        this.buttonList.add(this.slider_a);
        // TODO 增加这一项的开关

        this.btnSave = new GuiButton(id++, 3, button_top, button_width, 20, "Save");
        this.buttonList.add(this.btnSave);

        this.btnCancel = new GuiButton(id++, this.width / 2, button_top, button_width, 20, "Cancel");
        this.buttonList.add(this.btnCancel);
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground();

        drawString(this.fontRendererObj, "BlockId:", 5, 14, 0xFFFFFF);
        drawString(this.fontRendererObj, "Meta:", 5, 34, 0xFFFFFF);

        final int color = GuiUnit.colorToARGB((byte) (slider_r.percent * 255), (byte) (slider_g.percent * 255), (byte) (slider_b.percent * 255), (byte) (slider_a.percent * 255));
        Gui.drawRect(this.width / 3 * 2, this.height / 6, this.width - 30, this.height / 6 * 2, color);

        super.drawScreen(par1, par2, par3);
    }

    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnSave.id) {
                XrayBlock xrayBlock = new XrayBlock(
                    this.numericBlockId.getValue(),
                    (byte) this.numericMeta.getValue(),
                    (byte)(this.slider_r.percent * 255),
                    (byte)(this.slider_g.percent * 255),
                    (byte)(this.slider_b.percent * 255),
                    (byte)(this.slider_a.percent * 255)
                );
                if(this.xrayBlock == null) {
                    XrayBlock.add(xrayBlock);
                } else {
                    XrayBlock.set(xrayBlock, xrayBlockIndex);
                }
                XrayBlock.save();
                this.mc.displayGuiScreen(this.parentScreen);
            }
            if (guiButton.id == this.btnCancel.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }
}
