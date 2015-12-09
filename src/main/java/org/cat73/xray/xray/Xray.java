package org.cat73.xray.xray;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;

import org.cat73.xray.Cat_Xray;
import org.cat73.xray.util.CatBlockPos;
import org.cat73.xray.util.PlayerMessage;
import org.lwjgl.opengl.GL11;

public class Xray extends Thread{
    private final Minecraft mc;
    private final FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.getBlockRegistry();
    
    private final KeyBinding toggleXrayBinding;
    private final CatBlockPos pos = new CatBlockPos();
    private final CatBlockPos pos2 = new CatBlockPos();
    private final List<XrayBlockInfo> blockList = new ArrayList<XrayBlockInfo>();

    private final int displayListid;
    private boolean toggleXray = false;
    private boolean refresh = false;
    
    private int radius = 45;
    private int antiAntiXrayLevel = 0;
    private int interval = 5000;
    private int cooldown = 0;

    private Xray() {
        this.mc = Cat_Xray.getMC();

        reloadConfig();

        this.toggleXrayBinding = new KeyBinding("Toggle Xray", 45, "Cat-Xray");
        ClientRegistry.registerKeyBinding(this.toggleXrayBinding);

        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);

        this.displayListid = GL11.glGenLists(1);
        
        this.start();
    }

    public static void init() {
        new Xray();
    }

    public void run() {
        while(true) {
            if(cooldown-- == 0) {
                refresh();
                cooldown = interval;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
    
    private void refresh() {
        if (this.toggleXray && this.refresh == false) {
            final WorldClient world = this.mc.theWorld;
            final EntityPlayerSP player = this.mc.thePlayer;
            if (world != null && player != null) {
                this.blockList.clear();

                final int sx = (int) player.posX - this.radius;
                final int sz = (int) player.posZ - this.radius;
                final int endX = (int) player.posX + this.radius;
                final int endZ = (int) player.posZ + this.radius;
                int endY;

                Chunk chunk;
                IBlockState blockState;
                Block block;
                String blockName;
                XrayBlocks xrayBlock;
                int meta;
    
                for (int x = sx; x <= endX; x++) {
                    this.pos.setX(x);
                    for (int z = sz; z <= endZ; z++) {
                        this.pos.setZ(z);
                        chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
                        endY = chunk.getHeight(x & 15, z & 15);
                        for (int y = 0; y < endY; y++) {
                            this.pos.setY(y);
                            blockState = chunk.getBlockState(this.pos);
                            block = blockState.getBlock();
    
                            if (block != Blocks.air) {
                                blockName = String.valueOf(this.blockRegistery.getNameForObject(block));
                                xrayBlock = XrayBlocks.find(blockName);

                                if (xrayBlock != null) {
                                    if(xrayBlock.meta != -1) {
                                        meta = block.getMetaFromState(blockState);
                                        if(xrayBlock.meta != meta) {
                                            continue;
                                        }
                                    }
                                    if(this.antiAntiXrayLevel == 0 || antiAntiXray(x, y, z, world)) {
                                        blockList.add(new XrayBlockInfo(x, y, z, xrayBlock));
                                    }
                                }
                            }
                        }
                    }
                }
                refresh = true;
            }
        }
    }
    
    private boolean antiAntiXray(final int x, final int y, final int z, final WorldClient world) {
        boolean[] isTranslucents;
        if(this.antiAntiXrayLevel >= 1) {
            isTranslucents = new boolean[6];
            isTranslucents[0] = showBlock(world, x + 1, y, z);
            isTranslucents[1] = showBlock(world, x - 1, y, z);
            isTranslucents[2] = showBlock(world, x, y + 1, z);
            isTranslucents[3] = showBlock(world, x, y - 1, z);
            isTranslucents[4] = showBlock(world, x, y, z + 1);
            isTranslucents[5] = showBlock(world, x, y, z - 1);

            for(final boolean isTranslucent : isTranslucents) {
                if(isTranslucent) {
                    return true;
                }
            }
        }
        if(this.antiAntiXrayLevel >= 2) {
            isTranslucents = new boolean[12];
            isTranslucents[0] = showBlock(world, x + 1, y + 1, z);
            isTranslucents[1] = showBlock(world, x + 1, y - 1, z);
            isTranslucents[2] = showBlock(world, x - 1, y + 1, z);
            isTranslucents[3] = showBlock(world, x - 1, y - 1, z);
            isTranslucents[4] = showBlock(world, x, y + 1, z + 1);
            isTranslucents[5] = showBlock(world, x, y + 1, z - 1);
            isTranslucents[6] = showBlock(world, x, y - 1, z + 1);
            isTranslucents[7] = showBlock(world, x, y - 1, z - 1);
            isTranslucents[8] = showBlock(world, x + 1, y, z + 1);
            isTranslucents[9] = showBlock(world, x - 1, y, z + 1);
            isTranslucents[10] = showBlock(world, x + 1, y, z - 1);
            isTranslucents[11] = showBlock(world, x - 1, y, z - 1);

            for(final boolean isTranslucent : isTranslucents) {
                if(isTranslucent) {
                    return true;
                }
            }
        }
        if(this.antiAntiXrayLevel >= 3) {
            isTranslucents = new boolean[8];
            isTranslucents[0] = showBlock(world, x + 1, y + 1, z + 1);
            isTranslucents[1] = showBlock(world, x + 1, y + 1, z - 1);
            isTranslucents[2] = showBlock(world, x + 1, y - 1, z + 1);
            isTranslucents[3] = showBlock(world, x + 1, y - 1, z - 1);
            isTranslucents[4] = showBlock(world, x - 1, y + 1, z + 1);
            isTranslucents[5] = showBlock(world, x - 1, y + 1, z - 1);
            isTranslucents[6] = showBlock(world, x - 1, y - 1, z + 1);
            isTranslucents[7] = showBlock(world, x - 1, y - 1, z - 1);

            for(final boolean isTranslucent : isTranslucents) {
                if(isTranslucent) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean showBlock(final WorldClient world, final int x, final int y, final int z) {
        this.pos2.set(x, y, z);
        final Block block = world.getBlockState(this.pos2).getBlock();
        return block == Blocks.lava ||
               block.isTranslucent() || 
               block == Blocks.water ||
               block == Blocks.flowing_water ||
               block == Blocks.flowing_lava;
    }
    
    @SubscribeEvent
    public void keyboardEvent(final KeyInputEvent event) {
        if (this.toggleXrayBinding.isPressed()) {
            this.toggleXray = !this.toggleXray;
            if (this.toggleXray) {
                reloadConfig();
                cooldown = 0;
            } else {
                GL11.glDeleteLists(this.displayListid, 1);
            }
        }
    }

    @SubscribeEvent
    public void renderWorldLastEvent(final RenderWorldLastEvent event) {
        if (this.toggleXray && this.mc.theWorld != null) {
            final EntityPlayerSP player = this.mc.thePlayer;
            final double doubleX = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks;
            final double doubleY = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks;
            final double doubleZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks;

            GL11.glPushMatrix();
            GL11.glTranslated(-doubleX, -doubleY, -doubleZ);
            GL11.glCallList(this.displayListid);
            GL11.glPopMatrix();
        }
    }
    
    @SubscribeEvent
    public void onTickInGame(final ClientTickEvent event) {
        if (this.toggleXray && this.refresh && this.mc.thePlayer != null) {
            compileDL();
        }
    }

    private void compileDL() {
        GL11.glNewList(this.displayListid, GL11.GL_COMPILE);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glBegin(GL11.GL_LINES);

        for(final XrayBlockInfo xrayBlockInfo : this.blockList) {
            renderBlock(xrayBlockInfo.x, xrayBlockInfo.y, xrayBlockInfo.z, xrayBlockInfo.xrayBlock);
        }

        GL11.glEnd();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEndList();
        
        this.refresh = false;
    }

    private void renderBlock(final int x, final int y, final int z, final XrayBlocks block) {
        GL11.glColor4ub(block.r, block.g, block.b, block.a);

        GL11.glVertex3f(x, y, z);
        GL11.glVertex3f(x + 1, y, z);

        GL11.glVertex3f(x + 1, y, z);
        GL11.glVertex3f(x + 1, y, z + 1);

        GL11.glVertex3f(x, y, z);
        GL11.glVertex3f(x, y, z + 1);

        GL11.glVertex3f(x, y, z + 1);
        GL11.glVertex3f(x + 1, y, z + 1);

        GL11.glVertex3f(x, y + 1, z);
        GL11.glVertex3f(x + 1, y + 1, z);

        GL11.glVertex3f(x + 1, y + 1, z);
        GL11.glVertex3f(x + 1, y + 1, z + 1);

        GL11.glVertex3f(x, y + 1, z);
        GL11.glVertex3f(x, y + 1, z + 1);

        GL11.glVertex3f(x, y + 1, z + 1);
        GL11.glVertex3f(x + 1, y + 1, z + 1);

        GL11.glVertex3f(x, y, z);
        GL11.glVertex3f(x, y + 1, z);

        GL11.glVertex3f(x, y, z + 1);
        GL11.glVertex3f(x, y + 1, z + 1);

        GL11.glVertex3f(x + 1, y, z);
        GL11.glVertex3f(x + 1, y + 1, z);

        GL11.glVertex3f(x + 1, y, z + 1);
        GL11.glVertex3f(x + 1, y + 1, z + 1);
    }
    
    private void reloadConfig() {
        final Configuration config = Cat_Xray.getConfig();
        config.load();

        this.radius = config.get("Xray", "Radius", 45, "Radius for X-ray").getInt();
        this.interval = config.get("Xray", "Interval", 5, "Interval for X-ray(Seconds)").getInt() * 10;
        this.antiAntiXrayLevel = config.get("Xray", "AntiAntiXrayLevel", 0, "Anti Anti X-ray Level (0: off, 1~3: open)").getInt();
        if(this.radius < 0) {
            PlayerMessage.warn("Radius setting error!");
            this.radius = 45;
        }
        if(this.interval < 0) {
            PlayerMessage.warn("Interval setting error!");
            this.interval = 50;
        }
        if(this.antiAntiXrayLevel > 3 || this.antiAntiXrayLevel < 0) {
            PlayerMessage.warn("AntiAntiXrayLevel setting error!");
            this.antiAntiXrayLevel = 0;
        }

        XrayBlocks.load(config);

        config.save();
    }
}
