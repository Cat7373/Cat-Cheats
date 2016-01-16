package org.cat73.cheats.mods.xray;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;

import org.cat73.cheats.config.Config;
import org.cat73.cheats.config.XrayBlock;
import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModInfo;
import org.cat73.cheats.mods.xray.setting.Gui_Xray;
import org.cat73.cheats.util.CatBlockPos;
import org.cat73.cheats.util.ThreadUtil;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

@ModInfo(name="Xray", defaultHotkey=Keyboard.KEY_X)
public class Xray extends Mod implements Runnable {
    private final FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.getBlockRegistry();

    private final CatBlockPos pos = new CatBlockPos();
    private final CatBlockPos pos2 = new CatBlockPos();
    private final List<XrayBlockInfo> blockList = new ArrayList<XrayBlockInfo>();

    private int displayListid;
    private boolean refresh = false;
    private int cooldown = 0;

    private int radius = 45;
    private int antiAntiXrayLevel = 0;
    private int interval = 50;

    public Xray() {
        this.settingInstance = new Gui_Xray();
        final Thread refreshThread = new Thread(this, "Cat-Cheat Xray-Refresh");
        refreshThread.setDaemon(true);
        refreshThread.start();
    }

    @Override
    public void run() {
        while(true) {
            if(cooldown-- == 0) {
                refresh();
                cooldown = interval;
            }
            ThreadUtil.sleep(100);
        }
    }

    private void refresh() {
        if (this.enabled && this.refresh == false) {
            final WorldClient world = Mod.minecraft.theWorld;
            final EntityPlayerSP player = Mod.minecraft.thePlayer;
            if (world != null && player != null) {
                this.blockList.clear();

                final int sx = (int) player.posX - this.radius;
                final int sz = (int) player.posZ - this.radius;
                final int endX = (int) player.posX + this.radius;
                final int endZ = (int) player.posZ + this.radius;

                Chunk chunk;
                IBlockState blockState;
                Block block;
                int blockId;
                XrayBlock xrayBlock;
                byte damage;

                for (int x = sx; x <= endX; x++) {
                    this.pos.setX(x);
                    for (int z = sz; z <= endZ; z++) {
                        this.pos.setZ(z);
                        
                        chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
                        if(!chunk.isLoaded()) {
                            continue;
                        }
                        
                        for (int y = 0; y <= 255; y++) {
                            this.pos.setY(y);
                            blockState = chunk.getBlockState(this.pos);
                            block = blockState.getBlock();

                            if (block != Blocks.air) {
                                blockId = this.blockRegistery.getId(block);
                                damage = (byte) block.getMetaFromState(blockState);
                                xrayBlock = XrayBlock.find(blockId, damage);

                                if (xrayBlock != null) {
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

        final Chunk chunk = world.getChunkFromBlockCoords(this.pos2);
        if(!chunk.isLoaded()) {
            return false;
        }

        final Block block = chunk.getBlockState(this.pos2).getBlock();

        return block == Blocks.lava ||
               !block.getMaterial().isOpaque() ||
               block == Blocks.water ||
               block == Blocks.flowing_water ||
               block == Blocks.flowing_lava;
    }

    @Override
    public void onEnable() {
        getConfig();
        this.displayListid = GL11.glGenLists(1);
        cooldown = 0;
        
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @Override
    public void onDisable() {
        FMLCommonHandler.instance().bus().unregister(this);
        MinecraftForge.EVENT_BUS.unregister(this);

        GL11.glDeleteLists(this.displayListid, 1);
    }

    @SubscribeEvent
    public void renderWorldLastEvent(final RenderWorldLastEvent event) {
        final Entity entity = Mod.minecraft.getRenderViewEntity();
        final double doubleX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * event.partialTicks;
        final double doubleY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * event.partialTicks;
        final double doubleZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * event.partialTicks;

        GL11.glPushMatrix();
        GL11.glTranslated(-doubleX, -doubleY, -doubleZ);
        GL11.glCallList(this.displayListid);
        GL11.glPopMatrix();
    }

    @SubscribeEvent
    public void onTickInGame(final ClientTickEvent event) {
        if (this.refresh && Mod.minecraft.theWorld != null) {
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

    private void renderBlock(final int x, final int y, final int z, final XrayBlock block) {
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

    private void getConfig() {
        final Config config = Config.instance();
        this.radius = config.getIntConfig("xray.radius");
        this.interval = config.getIntConfig("xray.interval");
        this.antiAntiXrayLevel = config.getIntConfig("xray.antiantixraylevel");
    }
}
