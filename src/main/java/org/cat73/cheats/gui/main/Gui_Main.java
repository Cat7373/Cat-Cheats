package org.cat73.cheats.gui.main;

import java.io.IOException;

import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModSetting;

import com.github.lunatrius.core.client.gui.GuiScreenBase;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class Gui_Main extends GuiScreenBase {
    private Gui_Main_Mods_Slot mods_slot;
    private GuiButton btn_toggle;
    private GuiButton btn_setting;
    private GuiButton btn_setHotkey;
    private GuiButton btn_deleteHotkey;
    private GuiButton btn_exit;

    public Gui_Main(final GuiScreen guiScreen) {
        super(guiScreen);
    }
    
    @Override
    public void initGui() {
        super.initGui();

        final int button_top = this.height - 22;
        final int button_width = this.width / 10 * 2 - 2;
        int id = 0;

        this.btn_toggle = new GuiButton(id++, 1, button_top, button_width, 20, "Toggle Mod");
        this.buttonList.add(this.btn_toggle);

        this.btn_setting = new GuiButton(id++, this.width / 10 * 2 + 1, button_top, button_width, 20, "Mod Setting");
        this.buttonList.add(this.btn_setting);

        this.btn_exit = new GuiButton(id++, this.width / 10 * 8 + 1, button_top, button_width, 20, "Exit");
        this.buttonList.add(this.btn_exit);

        this.mods_slot = new Gui_Main_Mods_Slot(this);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        drawDefaultBackground();

        this.mods_slot.drawScreen(par1, par2, par3);
        super.drawScreen(par1, par2, par3);

        if(this.mods_slot.selectedIndex == -1) {
            this.btn_toggle.enabled = false;
            this.btn_setting.enabled = false;
        } else {
            this.btn_toggle.enabled = true;
            if(mods_slot.getSelectMod().settingClassName.equals("")) {
                this.btn_setting.enabled = false;
            } else {
                this.btn_setting.enabled = true;
            }
        }
    }
    
    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        this.mods_slot.handleMouseInput();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected void actionPerformed(final GuiButton guiButton) {
        if (guiButton.enabled) {
            if (guiButton.id == this.btn_toggle.id) {
                final Mod mod = mods_slot.getSelectMod();
                mod.toggle();
            } else if (guiButton.id == this.btn_setting.id) {
                final Mod mod = mods_slot.getSelectMod();

                Class<? extends ModSetting> settingClass;
                ModSetting modSetting;
                try {
                    settingClass = (Class<? extends ModSetting>) Class.forName(mod.settingClassName);
                    modSetting = settingClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                modSetting.show();
            } else if (guiButton.id == this.btn_exit.id) {
                this.mc.displayGuiScreen(this.parentScreen);
            } else {
                this.mods_slot.actionPerformed(guiButton);
            }
        }
    }
}
