package org.cat73.cheats.mods.freecam;

import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModInfo;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;

// TODO 防止离的太远的区块卸载
@ModInfo(name="FreeCam")
public class FreeCam extends Mod {
    private FreeCamPlayer ghostPlayer = null;

    @Override
    public void onEnable() {
        final EntityClientPlayerMP player = Mod.minecraft.thePlayer;
        final WorldClient world = Mod.minecraft.theWorld;

        this.ghostPlayer = new FreeCamPlayer(world, player.getGameProfile());
        this.ghostPlayer.copyLocationAndAnglesFrom(player);

        world.spawnEntityInWorld(this.ghostPlayer);
        Mod.minecraft.renderViewEntity = this.ghostPlayer;
    }
    
    @Override
    public void onDisable() {
        final EntityClientPlayerMP player = Mod.minecraft.thePlayer;
        final WorldClient world = Mod.minecraft.theWorld;

        world.removeEntity(this.ghostPlayer);
        Mod.minecraft.renderViewEntity = player;
        Mod.minecraft.renderGlobal.loadRenderers();
    }
}
