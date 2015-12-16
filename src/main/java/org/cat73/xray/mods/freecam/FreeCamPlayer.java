package org.cat73.xray.mods.freecam;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;

public class FreeCamPlayer extends EntityPlayer {
    protected final static Minecraft minecraft = Minecraft.getMinecraft();

    public FreeCamPlayer(final World worldIn, final GameProfile gameProfile) {
        super(worldIn, gameProfile);
        this.capabilities.allowFlying = true;
        this.capabilities.isFlying = true;
        this.noClip = true;
    }
    
    @Override
    public void onLivingUpdate() {
        final EntityPlayerSP player = FreeCamPlayer.minecraft.thePlayer;
        if(player != null) {
            // 刷新键盘输入
            final MovementInput movementInput = player.movementInput;
            movementInput.updatePlayerMoveState();

            // 刷新角度 物品栏
            this.rotationPitch = player.rotationPitch;
            this.rotationYaw = player.rotationYaw;
            this.rotationYawHead = player.rotationYawHead;
            this.inventory = player.inventory;

            // TODO 更平滑的加速与减速
            // 刷新速度
            final float flySpeed = this.capabilities.getFlySpeed();
            final float strafe = movementInput.moveStrafe * flySpeed * 6.0F;
            final float forward = movementInput.moveForward * flySpeed * 6.0F;
            final float sin = MathHelper.sin(this.rotationYaw * (float)Math.PI / 180.0F);
            final float cos = MathHelper.cos(this.rotationYaw * (float)Math.PI / 180.0F);

            // 运动量计算
            final double motionX = strafe * cos - forward * sin;
            double motionY = 0.0D;
            final double motionZ = forward * cos + strafe * sin;
            if(movementInput.jump) {
                motionY = flySpeed * 5.0F;
            } else if(movementInput.sneak) {
                motionY = flySpeed * -5.0F;
            }
            
            // 移动实体
            this.posX += motionX;
            this.posY += motionY;
            this.posZ += motionZ;
        }
    }

    @Override
    public boolean isSpectator() {
        return false;
    }
}
