package org.cat73.cheats.mods.freecam;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.world.World;

//TODO 像真正的观察者模式一样 可以吸附在其他生物身上
public class FreeCamPlayer extends EntityPlayer {
    private final static Minecraft minecraft = Minecraft.getMinecraft();

    public FreeCamPlayer(final World worldIn, final GameProfile gameProfile) {
        super(worldIn, gameProfile);
    }
    
    @Override
    public void onLivingUpdate() {
        final EntityClientPlayerMP player = FreeCamPlayer.minecraft.thePlayer;
        if(player != null) {
            // 刷新键盘输入
            final MovementInput movementInput = player.movementInput;
            movementInput.updatePlayerMoveState();

            // TODO 防止 Shift Ctrl 对原玩家生效
            // 防止原 Player 移动
            player.motionX = 0.0D;
            player.motionY = 0.0D;
            player.motionZ = 0.0D;

            // 刷新角度 物品栏
            this.setHealth(player.getHealth());
            this.foodStats = player.getFoodStats();
            this.rotationPitch = player.rotationPitch;
            this.rotationYaw = player.rotationYaw;
            this.rotationYawHead = player.rotationYawHead;
            this.inventory = player.inventory;

            // TODO 更平滑的加速与减速
            // 刷新速度
            float flySpeed = this.capabilities.getFlySpeed();
            if(FreeCamPlayer.minecraft.gameSettings.keyBindSprint.getIsKeyPressed()) {
                flySpeed *= 1.8;
            }
            
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
    public void addChatMessage(IChatComponent message) {
        FreeCamPlayer.minecraft.thePlayer.addChatMessage(message);
    }

    @Override
    public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_) {
        return FreeCamPlayer.minecraft.thePlayer.canCommandSenderUseCommand(p_70003_1_, p_70003_2_);
    }

    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return FreeCamPlayer.minecraft.thePlayer.getPlayerCoordinates();
    }
}
