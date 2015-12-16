package org.cat73.xray.mods.freecam;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;

public class FreeCamPlayer extends EntityPlayer {
    public MovementInput movementInput;

    public FreeCamPlayer(final World worldIn, final GameProfile gameProfile) {
        super(worldIn, gameProfile);
    }
    
    @Override
    public void onLivingUpdate() {
        this.movementInput.updatePlayerMoveState();
        this.moveEntityWithHeading(this.movementInput.moveStrafe, this.movementInput.moveForward);
        
        this.capabilities.allowFlying = true;
        this.capabilities.isFlying = true;
        this.noClip = true;

        this.moveFlying(this.movementInput.moveStrafe, this.movementInput.moveForward, 0.7F);
        this.posX += this.motionX;
        this.posZ += this.motionZ;
        this.motionX = 0;
        this.motionZ = 0;
        
        if(this.movementInput.jump) {
            this.posY +=  this.capabilities.getFlySpeed() * 8.0F;
        } else if(this.movementInput.sneak) {
            this.posY -=  this.capabilities.getFlySpeed() * 8.0F;
        }
        
        super.onLivingUpdate();
    }

    @Override
    public boolean isSpectator() {
        return false;
    }
}
