package org.cat73.xray.mods.fullbright;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

import org.cat73.xray.mods.Mod;
import org.cat73.xray.mods.ModInfo;

@ModInfo(name="Fullbright")
public class Fullbright extends Mod {
    private float gamma;
    
    @Override
    public void onInit() {
        FMLCommonHandler.instance().bus().register(this);
        
        setGamma();
        onDisable();
    }
    
    @Override
    public void onEnable() {
        setGamma();

        Mod.minecraft.gameSettings.gammaSetting = 16.0F;
    }
    
    @Override
    public void onDisable() {
        Mod.minecraft.gameSettings.gammaSetting = this.gamma;
    }
    
    @SubscribeEvent
    public void onTickInGame(final ClientTickEvent event) {
        if(this.enabled) {
            final EntityPlayerSP player = Mod.minecraft.thePlayer;
            if(player != null) {
                player.removePotionEffectClient(Potion.blindness.id);
                player.removePotionEffectClient(Potion.confusion.id);
            }
        }
    }
    
    private void setGamma() {
        this.gamma = Mod.minecraft.gameSettings.gammaSetting;
        this.gamma = this.gamma > 1.0F ? 1.0F : this.gamma;
    }
}
