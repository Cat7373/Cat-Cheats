package org.cat73.cheats.mods.blockxray.setting.addblock;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class GuiSlider extends GuiButton {
    protected float percent;
    private boolean isClicked;

    public GuiSlider(final int id, final int x, final int y, final String name, final float percentage) {
        super(id, x, y, 150, 20, name);
        this.percent = percentage;
    }

    @Override
    protected int getHoverState(final boolean mouseOver) {
        return 0;
    }

    @Override
    protected void mouseDragged(final Minecraft mc, final int mouseX, final int mouseY) {
        if (this.visible) {
            if (this.isClicked) {
                this.percent = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);

                if (this.percent < 0.0F) {
                    this.percent = 0.0F;
                }

                if (this.percent > 1.0F) {
                    this.percent = 1.0F;
                }
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int) (this.percent * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.percent * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(final Minecraft mc, final int mouseX, final int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            this.percent = (float) (mouseX - (this.xPosition + 4)) / (float) (this.width - 8);

            if (this.percent < 0.0F) {
                this.percent = 0.0F;
            }

            if (this.percent > 1.0F) {
                this.percent = 1.0F;
            }

            this.isClicked = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void mouseReleased(final int mouseX, final int mouseY) {
        this.isClicked = false;
    }
}
