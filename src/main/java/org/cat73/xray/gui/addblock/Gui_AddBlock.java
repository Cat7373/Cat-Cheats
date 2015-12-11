package org.cat73.xray.gui.addblock;

import org.cat73.xray.config.XrayBlock;

import net.minecraft.block.Block;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;

import com.github.lunatrius.core.client.gui.GuiNumericField;
import com.github.lunatrius.core.client.gui.GuiScreenBase;

public class Gui_AddBlock extends GuiScreenBase {
    private final static FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.getBlockRegistry();

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

    // TODO 窗口大小变化后值会重置
    @Override
    public void initGui() {
        this.buttonList.clear();

        final int button_top = this.height - 22;
        final int button_width = this.width / 2 - 3;
        int id = 0;

        this.numericBlockId = new GuiNumericField(this.fontRendererObj, id++, 90, 10, (int) (this.width / 3));
        this.numericBlockId.setMinimum(0);
        this.numericBlockId.setValue(this.xrayBlock == null ? 0 : this.xrayBlock.id);
        this.buttonList.add(this.numericBlockId);

        this.numericMeta = new GuiNumericField(this.fontRendererObj, id++, 90, 30, (int) (this.width / 3));
        this.numericMeta.setMinimum(-1);
        this.numericMeta.setMaximum(15);
        this.numericMeta.setValue(this.xrayBlock == null ? 0 : this.xrayBlock.meta);
        this.buttonList.add(this.numericMeta);

        this.slider_r = new GuiSlider(id++, this.width - 160, this.height / 10 * 5, "Red-Value", this.xrayBlock == null ? 0.5f : (this.xrayBlock.r & 255) / 255.0f);
        this.buttonList.add(this.slider_r);
        this.slider_g = new GuiSlider(id++, this.width - 160, this.height / 10 * 6, "Green-Value", this.xrayBlock == null ? 0.5f : (this.xrayBlock.g & 255) / 255.0f);
        this.buttonList.add(this.slider_g);
        this.slider_b = new GuiSlider(id++, this.width - 160, this.height / 10 * 7, "Blue-Value", this.xrayBlock == null ? 0.5f : (this.xrayBlock.b & 255) / 255.0f);
        this.buttonList.add(this.slider_b);
        this.slider_a = new GuiSlider(id++, this.width - 160, this.height / 10 * 8, "Alpha-Value", this.xrayBlock == null ? 1.0f : (this.xrayBlock.a & 255) / 255.0f);
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

        // TODO 颜色计算挪到 unit 中
        final int color = (((int)(slider_a.percent * 255) << 24) & 0xff000000) | (((int)(slider_r.percent * 255) << 16) & 0x00ff0000) | (((int)(slider_g.percent * 255) << 8) & 0x0000ff00) | ((int)(slider_b.percent * 255) & 0x000000ff);
        Gui.drawRect(this.width / 3 * 2, this.height / 6, this.width - 30, this.height / 6 * 2, color);

        super.drawScreen(par1, par2, par3);
    }

    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btnSave.id) {
                XrayBlock.set(XrayBlock.fromString(this.toString()), xrayBlockIndex);
                XrayBlock.save();
                this.mc.displayGuiScreen(this.parentScreen);
            }
            if (guiButton.id == this.btnCancel.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }

    @Override
    public String toString() {
        // TODO 一些诸如方块id转名称的方法放进util包里
        final Block block = blockRegistery.getObjectById(this.numericBlockId.getValue());
        return blockRegistery.getNameForObject(block).toString() + " " + this.numericMeta.getValue() + " " + ((byte)(this.slider_r.percent * 255)) + " " + ((byte)(this.slider_g.percent * 255)) + " " + ((byte)(this.slider_b.percent * 255)) + " " + ((byte)(this.slider_a.percent * 255));
    }
}
