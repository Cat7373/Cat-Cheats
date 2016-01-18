package org.cat73.cheats.mods.freecam;

import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModInfo;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.MovementInput;

// TODO 防止离的太远的区块卸载
@ModInfo(name="FreeCam")
public class FreeCam extends Mod {
    private EntityPlayerSP player = null;
    private FreeCamPlayer ghostPlayer = null;

    @Override
    public void onEnable() {
        this.player = Mod.minecraft.thePlayer;
        final WorldClient world = Mod.minecraft.theWorld;

        this.ghostPlayer = new FreeCamPlayer(world, this.player.getGameProfile(), this.player.movementInput);
        this.ghostPlayer.copyLocationAndAnglesFrom(this.player);
        
        MovementInput movementInput = new MovementInput();
        movementInput.jump = false;
        movementInput.sneak = false;
        movementInput.moveStrafe = 0.0F;
        movementInput.moveForward = 0.0F;
        this.player.movementInput = movementInput;

        world.spawnEntityInWorld(this.ghostPlayer);
        Mod.minecraft.setRenderViewEntity(this.ghostPlayer);
    }
    
    @Override
    public void onDisable() {
        if(this.player == Mod.minecraft.thePlayer) {
            this.player.movementInput = this.ghostPlayer.movementInput;
        } else {
            this.player = Mod.minecraft.thePlayer;
        }

        final WorldClient world = Mod.minecraft.theWorld;

        world.removeEntity(this.ghostPlayer);
        Mod.minecraft.setRenderViewEntity(this.player);
        Mod.minecraft.renderGlobal.loadRenderers();
        
        this.player = null;
        this.ghostPlayer = null;
    }
}
