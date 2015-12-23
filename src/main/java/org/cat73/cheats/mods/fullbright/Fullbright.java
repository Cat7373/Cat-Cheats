package org.cat73.cheats.mods.fullbright;

import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModInfo;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

@ModInfo(name="Fullbright")
public class Fullbright extends Mod {
    private final GameSettings gameSettings;
    private float gamma;
    
    public Fullbright() {
        this.gameSettings = Mod.minecraft.gameSettings;
        setGamma();
        this.gameSettings.gammaSetting = this.gamma;
    }

    @Override
    public void onEnable() {
        setGamma();
        this.gameSettings.gammaSetting = 16.0F;

        FMLCommonHandler.instance().bus().register(this);
    }
    
    @Override
    public void onDisable() {
        FMLCommonHandler.instance().bus().unregister(this);

        this.gameSettings.gammaSetting = this.gamma;
    }
    
    @SubscribeEvent
    public void onTickInGame(final ClientTickEvent event) {
        final EntityPlayerSP player = Mod.minecraft.thePlayer;
        if(player != null) {
            player.removePotionEffectClient(Potion.blindness.id);
            player.removePotionEffectClient(Potion.confusion.id);
        }
    }
    
    private void setGamma() {
        this.gamma = this.gameSettings.gammaSetting;
        this.gamma = this.gamma > 1.0F ? 1.0F : this.gamma;
    }
}
