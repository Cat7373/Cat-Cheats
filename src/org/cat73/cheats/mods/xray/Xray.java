package org.cat73.cheats.mods.xray;

import java.util.ArrayList;
import java.util.List;

import org.cat73.cheats.config.Config;
import org.cat73.cheats.config.XrayBlock;
import org.cat73.cheats.mods.Mod;
import org.cat73.cheats.mods.ModInfo;
import org.cat73.cheats.mods.xray.setting.Gui_Xray;
import org.cat73.cheats.util.ThreadUtil;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;

@ModInfo(name="Xray", defaultHotkey=Keyboard.KEY_X)
public class Xray extends Mod implements Runnable {
    private final FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.blockRegistry;

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
            final EntityClientPlayerMP player = Mod.minecraft.thePlayer;
            if (world != null && player != null) {
                this.blockList.clear();

                final int sx = (int) player.posX - this.radius;
                final int sz = (int) player.posZ - this.radius;
                final int endX = (int) player.posX + this.radius;
                final int endZ = (int) player.posZ + this.radius;

                Block block;
                int blockId;
                XrayBlock xrayBlock;
                byte damage;
                Chunk chunk;

                for (int x = sx; x <= endX; x++) {
                    for (int z = sz; z <= endZ; z++) {
                        
                        chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
                        if(!chunk.isChunkLoaded) {
                            continue;
                        }
                        
                        for (int y = 0; y <= 255; y++) {
                            block = chunk.getBlock(x & 15, y, z & 15);

                            if (block != Blocks.air) {
                                blockId = this.blockRegistery.getId(block);
                                damage = (byte) chunk.getBlockMetadata(x & 15, y, z & 15);
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
        return showBlock(world, x, y, z) ||
                showBlock(world, x + 1, y, z) ||
                showBlock(world, x - 1, y, z) ||
                showBlock(world, x, y + 1, z) ||
                showBlock(world, x, y - 1, z) ||
                showBlock(world, x, y, z + 1) ||
                showBlock(world, x, y, z - 1);
    }

    private boolean showBlock(final WorldClient world, final int x, final int y, final int z) {
        final Chunk chunk = world.getChunkFromChunkCoords(x >> 4, z >> 4);
        if(!chunk.isChunkLoaded) {
            return false;
        }
        
        final Block block = chunk.getBlock(x & 15, y, z & 15);

        return !block.isNormalCube();
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
        final Entity entity = Mod.minecraft.renderViewEntity;
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

        XrayBlock xrayBlock = null;

        for(final XrayBlockInfo xrayBlockInfo : this.blockList) {
            if(xrayBlockInfo.xrayBlock != xrayBlock) {
                xrayBlock = xrayBlockInfo.xrayBlock;
                GL11.glColor4ub(xrayBlock.r, xrayBlock.g, xrayBlock.b, xrayBlock.a);
            }

            renderBlock(xrayBlockInfo.x, xrayBlockInfo.y, xrayBlockInfo.z);
        }

        GL11.glEnd();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEndList();

        this.refresh = false;
    }

    private void renderBlock(final int x, final int y, final int z) {
        GL11.glVertex3i(x, y, z);
        GL11.glVertex3i(x + 1, y, z);

        GL11.glVertex3i(x + 1, y, z);
        GL11.glVertex3i(x + 1, y, z + 1);

        GL11.glVertex3i(x, y, z);
        GL11.glVertex3i(x, y, z + 1);

        GL11.glVertex3i(x, y, z + 1);
        GL11.glVertex3i(x + 1, y, z + 1);

        GL11.glVertex3i(x, y + 1, z);
        GL11.glVertex3i(x + 1, y + 1, z);

        GL11.glVertex3i(x + 1, y + 1, z);
        GL11.glVertex3i(x + 1, y + 1, z + 1);

        GL11.glVertex3i(x, y + 1, z);
        GL11.glVertex3i(x, y + 1, z + 1);

        GL11.glVertex3i(x, y + 1, z + 1);
        GL11.glVertex3i(x + 1, y + 1, z + 1);

        GL11.glVertex3i(x, y, z);
        GL11.glVertex3i(x, y + 1, z);

        GL11.glVertex3i(x, y, z + 1);
        GL11.glVertex3i(x, y + 1, z + 1);

        GL11.glVertex3i(x + 1, y, z);
        GL11.glVertex3i(x + 1, y + 1, z);

        GL11.glVertex3i(x + 1, y, z + 1);
        GL11.glVertex3i(x + 1, y + 1, z + 1);
    }

    private void getConfig() {
        final Config config = Config.instance();
        this.radius = config.getIntConfig("xray.radius");
        this.interval = config.getIntConfig("xray.interval");
        this.antiAntiXrayLevel = config.getIntConfig("xray.antiantixraylevel");
    }
}
