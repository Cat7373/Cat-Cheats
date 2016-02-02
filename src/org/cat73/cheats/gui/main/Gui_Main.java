package org.cat73.cheats.gui.main;

import org.cat73.cheats.gui.tool.GuiScreenBase;
import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModManager;
import org.cat73.cheats.reference.Names;
import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class Gui_Main extends GuiScreenBase {
    private final String str_toggle = I18n.format(Names.Gui.TOGGLE);
    private final String str_setting = I18n.format(Names.Gui.SETTING);
    private final String str_setHotkey = I18n.format(Names.Gui.SETHOTKEY);
    private final String str_exit = I18n.format(Names.Gui.EXIT);

    private Gui_Main_Mods_Slot mods_slot;
    private GuiButton btn_toggle;
    private GuiButton btn_setting;
    private GuiButton btn_setHotkey;
    private GuiButton btn_exit;
    private boolean setHotkey;

    public Gui_Main(final GuiScreen guiScreen) {
        super(guiScreen);
    }

    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        this.setHotkey = false;

        if (guiButton.enabled) {
            if (guiButton.id == this.btn_toggle.id) {
                final Mod mod = this.mods_slot.getSelectMod();
                mod.toggle();
            } else if (guiButton.id == this.btn_setting.id) {
                final Mod mod = this.mods_slot.getSelectMod();
                mod.getSettingInstance().show();
            } else if (guiButton.id == this.btn_setHotkey.id) {
                this.setHotkey = true;
                this.btn_setHotkey.displayString = "> ? <";
            } else if (guiButton.id == this.btn_exit.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            } else {
                this.mods_slot.actionPerformed(guiButton);
            }
        }
    }

    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();

        this.mods_slot.drawScreen(par1, par2, par3);
        super.drawScreen(par1, par2, par3);

        if (this.mods_slot.selectedIndex == -1) {
            this.btn_toggle.enabled = false;
            this.btn_setting.enabled = false;
            this.btn_setHotkey.enabled = false;
        } else {
            this.btn_toggle.enabled = true;

            if (this.mods_slot.getSelectMod().getSettingInstance() == null) {
                this.btn_setting.enabled = false;
            } else {
                this.btn_setting.enabled = true;
            }

            this.btn_setHotkey.enabled = true;
        }
    }

    @Override
    public void initGui() {
        super.initGui();

        final int button_top = this.height - 22;
        final int button_width = (this.width / 4) - 2;
        int id = 0;

        this.btn_toggle = new GuiButton(id++, 1, button_top, button_width, 20, this.str_toggle);
        this.buttonList.add(this.btn_toggle);

        this.btn_setting = new GuiButton(id++, (this.width / 4) + 1, button_top, button_width, 20, this.str_setting);
        this.buttonList.add(this.btn_setting);

        this.btn_setHotkey = new GuiButton(id++, ((this.width / 4) * 2) + 1, button_top, button_width, 20, this.str_setHotkey);
        this.buttonList.add(this.btn_setHotkey);

        this.btn_exit = new GuiButton(id++, ((this.width / 4) * 3) + 1, button_top, button_width, 20, this.str_exit);
        this.buttonList.add(this.btn_exit);

        this.mods_slot = new Gui_Main_Mods_Slot(this);

        this.setHotkey = false;
    }

    @Override
    protected void keyTyped(final char character, int code) {
        if (this.setHotkey) {
            this.setHotkey = false;

            code = code != Keyboard.KEY_ESCAPE ? code : Keyboard.KEY_NONE;

            this.btn_setHotkey.displayString = this.str_setHotkey;
            ModManager.setHotKey(this.mods_slot.getSelectMod(), code);
        } else {
            super.keyTyped(character, code);
        }
    }
}
