package org.cat73.xray.mods.freecam;

import org.cat73.xray.mods.Mod;
import org.cat73.xray.mods.ModInfo;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

// TODO 防止离的太远的区块卸载
@ModInfo(name="FreeCam")
public class FreeCam extends Mod {
    private FreeCamPlayer ghostPlayer = null;

    @Override
    public void onEnable() {
        final EntityPlayerSP player = Mod.minecraft.thePlayer;
        final WorldClient world = Mod.minecraft.theWorld;

        this.ghostPlayer = new FreeCamPlayer(world, player.getGameProfile());
        this.ghostPlayer.copyLocationAndAnglesFrom(Mod.minecraft.thePlayer);
        this.ghostPlayer.movementInput = player.movementInput;

        world.spawnEntityInWorld(this.ghostPlayer);
        Mod.minecraft.setRenderViewEntity(this.ghostPlayer);

        FMLCommonHandler.instance().bus().register(this);
    }
    
    @Override
    public void onDisable() {
        FMLCommonHandler.instance().bus().unregister(this);
        
        final EntityPlayerSP player = Mod.minecraft.thePlayer;
        final WorldClient world = Mod.minecraft.theWorld;

        world.removeEntity(this.ghostPlayer);
        Mod.minecraft.setRenderViewEntity(player);
        // TODO 刷新显示
    }

    @SubscribeEvent
    public void onTickInGame(final ClientTickEvent event) {
        final EntityPlayerSP player = Mod.minecraft.thePlayer;
        if(player != null) {
            this.ghostPlayer.rotationPitch = player.rotationPitch;
            this.ghostPlayer.rotationYaw = player.rotationYaw;
            this.ghostPlayer.rotationYawHead = player.rotationYawHead;
            this.ghostPlayer.inventory = Mod.minecraft.thePlayer.inventory;
            this.ghostPlayer.movementInput = player.movementInput;
        }
    }
}
