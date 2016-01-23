package org.cat73.cheats.mods.fullbright;

import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModInfo;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;

@ModInfo(name="Fullbright")
public class Fullbright extends Mod {
    private final GameSettings gameSettings;
    private float gamma;
    private int cooldown;
    
    public Fullbright() {
        this.gameSettings = Mod.minecraft.gameSettings;
        setGamma();
        this.gameSettings.gammaSetting = this.gamma;
    }

    @Override
    public void onEnable() {
        setGamma();
        this.gameSettings.gammaSetting = 16.0F;
        this.cooldown = 0;

        FMLCommonHandler.instance().bus().register(this);
    }
    
    @Override
    public void onDisable() {
        FMLCommonHandler.instance().bus().unregister(this);

        this.gameSettings.gammaSetting = this.gamma;
    }
    
    @SubscribeEvent
    public void onTickInGame(final ClientTickEvent event) {
        if(event.phase != Phase.END) {
            return;
        }

        if(this.cooldown-- == 0) {
            final EntityClientPlayerMP player = Mod.minecraft.thePlayer;
            if(player != null) {
                player.removePotionEffectClient(Potion.blindness.id);
                player.removePotionEffectClient(Potion.confusion.id);
            }
            
            this.cooldown = 10;
        }
    }
    
    private void setGamma() {
        this.gamma = this.gameSettings.gammaSetting;
        this.gamma = this.gamma > 1.0F ? 1.0F : this.gamma;
    }
}
