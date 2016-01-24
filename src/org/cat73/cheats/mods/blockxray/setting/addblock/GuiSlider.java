package org.cat73.cheats.mods.blockxray.setting.addblock;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class GuiSlider extends GuiButton {
    protected float percent;
    private boolean isClicked;

    public GuiSlider(int id, int x, int y, String name, float percentage) {
        super(id, x, y, 150, 20, name);
        this.percent = percentage;
    }

    @Override
    public int getHoverState(boolean mouseOver) {
        return 0;
    }

    @Override
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            if (this.isClicked) {
                this.percent = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);

                if (this.percent < 0.0F) {
                    this.percent = 0.0F;
                }

                if (this.percent > 1.0F) {
                    this.percent = 1.0F;
                }
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int)(this.percent * (float)(this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.percent * (float)(this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            this.percent = (float)(mouseX - (this.xPosition + 4)) / (float)(this.width - 8);

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
    public void mouseReleased(int mouseX, int mouseY) {
        this.isClicked = false;
    }
}
