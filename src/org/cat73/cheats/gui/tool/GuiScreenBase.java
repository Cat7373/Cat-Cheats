package org.cat73.cheats.gui.tool;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

public class GuiScreenBase extends GuiScreen {
    protected final GuiScreen parentScreen;

    protected List<GuiButton> buttonList = super.buttonList;
    protected List<GuiTextField> textFields = new ArrayList<GuiTextField>();

    public GuiScreenBase() {
        this(null);
    }

    public GuiScreenBase(final GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        for (final GuiTextField textField : this.textFields) {
            textField.drawTextBox();
        }
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);

        this.buttonList.clear();
        this.textFields.clear();
    }

    @Override
    protected void keyTyped(final char character, final int code) {
        if (code == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(this.parentScreen);
            return;
        }

        for (final GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField) {
                final GuiNumericField numericField = (GuiNumericField) button;
                numericField.keyTyped(character, code);

                if (numericField.isFocused()) {
                    this.actionPerformed(numericField);
                }
            }
        }

        for (final GuiTextField textField : this.textFields) {
            textField.textboxKeyTyped(character, code);
        }

        super.keyTyped(character, code);
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseEvent) {
        for (final GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField) {
                final GuiNumericField numericField = (GuiNumericField) button;
                numericField.mouseClicked(mouseX, mouseY, mouseEvent);
            }
        }

        for (final GuiTextField textField : this.textFields) {
            textField.mouseClicked(mouseX, mouseY, mouseEvent);
        }

        super.mouseClicked(mouseX, mouseY, mouseEvent);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        for (final GuiButton button : this.buttonList) {
            if (button instanceof GuiNumericField) {
                final GuiNumericField numericField = (GuiNumericField) button;
                numericField.updateCursorCounter();
            }
        }

        for (final GuiTextField textField : this.textFields) {
            textField.updateCursorCounter();
        }
    }
}
