package org.cat73.xray;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.cat73.xray.util.CatBlockPos;
import org.lwjgl.opengl.GL11;

@Mod(modid = Cat_Xray.MODID, version = Cat_Xray.VERSION)
public class Cat_Xray {
    public static final String MODID = "Cat-Xray";
    public static final String VERSION = "0.0.1";
    public static final boolean DEBUG = true;
    private boolean toggleXray = false;
    private int radius = 45;
    private KeyBinding toggleXrayBinding;
    private int displayListid = 0;
    private int cooldownTicks = 0;
    private int antiAntiXrayLevel = 0;
    private Minecraft mc;
    private Configuration config;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (event.getSide() == Side.SERVER) {
            return;
        }

        config = new Configuration(event.getSuggestedConfigurationFile());
        reloadConfig();

        this.toggleXrayBinding = new KeyBinding("Toggle Xray", 45, "Cat-Xray");

        ClientRegistry.registerKeyBinding(this.toggleXrayBinding);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide() == Side.SERVER) {
            return;
        }

        this.mc = Minecraft.getMinecraft();

        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (event.getSide() == Side.SERVER) {
            return;
        }
        displayListid = GL11.glGenLists(5) + 3;
    }

    @SubscribeEvent
    public boolean onTickInGame(ClientTickEvent e) {
        if ((!toggleXray) || (this.mc.thePlayer == null)) {
            return true;
        }

        if (cooldownTicks < 1) {
            compileDL();
            cooldownTicks = antiAntiXrayLevel == 0 ? 10 * 20 : 20 * 5;
        }

        cooldownTicks -= 1;
        return true;
    }

    private void compileDL() {
        GL11.glNewList(displayListid, 4864);

        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);

        GL11.glBegin(1);

        final WorldClient world = this.mc.theWorld;
        final EntityPlayerSP player = this.mc.thePlayer;
        if ((world == null) || (player == null)) {
            return;
        }

        final int sx = (int) player.posX - radius;
        final int sz = (int) player.posZ - radius;
        final int endX = (int) player.posX + radius;
        final int endZ = (int) player.posZ + radius;

        final CatBlockPos pos = new CatBlockPos();
        
        for (int x = sx; x <= endX; x++) {
            for (int z = sz; z <= endZ; z++) {
                final int endY = world.getChunkFromChunkCoords(x >> 4, z >> 4).getHeight(x & 15, z & 15);
                for (int y = 0; y <= endY; y++) {
                    pos.set(x, y, z);
                    final IBlockState blockState = world.getBlockState(pos);
                    final Block block = blockState.getBlock();

                    if (block != Blocks.air) {
                        final int meta = block.getMetaFromState(blockState);
                        final String blockName = String.valueOf(GameData.getBlockRegistry().getNameForObject(block));

                        for (XrayBlocks xrayBlock : XrayBlocks.blocks) {
                            if (xrayBlock.name.equalsIgnoreCase(blockName) && ((xrayBlock.meta == -1) || (xrayBlock.meta == meta))) {
                                if(antiAntiXrayLevel == 0 || antiAntiXray(x, y, z, world)) {
                                    renderBlock(x, y, z, xrayBlock);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        GL11.glEnd();
        GL11.glEnable(2929);
        GL11.glDisable(3042);
        GL11.glEnable(3553);
        GL11.glEndList();
    }

    private boolean antiAntiXray(int x, int y, int z, WorldClient world) {
        if(antiAntiXrayLevel >= 1) {
            boolean[] isTranslucents = new boolean[6];
            isTranslucents[0] = blockIsTranslucent(world, x + 1, y, z);
            isTranslucents[1] = blockIsTranslucent(world, x - 1, y, z);
            isTranslucents[2] = blockIsTranslucent(world, x, y + 1, z);
            isTranslucents[3] = blockIsTranslucent(world, x, y - 1, z);
            isTranslucents[4] = blockIsTranslucent(world, x, y, z + 1);
            isTranslucents[5] = blockIsTranslucent(world, x, y, z - 1);
    
            for(boolean isTranslucent : isTranslucents) {
                if(isTranslucent) {
                    return true;
                }
            }
        }
        if(antiAntiXrayLevel >= 2) {
            boolean[] isTranslucents = new boolean[12];
            isTranslucents[0] = blockIsTranslucent(world, x + 1, y + 1, z);
            isTranslucents[1] = blockIsTranslucent(world, x + 1, y - 1, z);
            isTranslucents[2] = blockIsTranslucent(world, x - 1, y + 1, z);
            isTranslucents[3] = blockIsTranslucent(world, x - 1, y - 1, z);
            isTranslucents[4] = blockIsTranslucent(world, x, y + 1, z + 1);
            isTranslucents[5] = blockIsTranslucent(world, x, y + 1, z - 1);
            isTranslucents[6] = blockIsTranslucent(world, x, y - 1, z + 1);
            isTranslucents[7] = blockIsTranslucent(world, x, y - 1, z - 1);
            isTranslucents[8] = blockIsTranslucent(world, x + 1, y, z + 1);
            isTranslucents[9] = blockIsTranslucent(world, x - 1, y, z + 1);
            isTranslucents[10] = blockIsTranslucent(world, x + 1, y, z - 1);
            isTranslucents[11] = blockIsTranslucent(world, x - 1, y, z - 1);
    
            for(boolean isTranslucent : isTranslucents) {
                if(isTranslucent) {
                    return true;
                }
            }
        }
        if(antiAntiXrayLevel >= 3) {
            boolean[] isTranslucents = new boolean[8];
            isTranslucents[0] = blockIsTranslucent(world, x + 1, y + 1, z + 1);
            isTranslucents[1] = blockIsTranslucent(world, x + 1, y + 1, z - 1);
            isTranslucents[2] = blockIsTranslucent(world, x + 1, y - 1, z + 1);
            isTranslucents[3] = blockIsTranslucent(world, x + 1, y - 1, z - 1);
            isTranslucents[4] = blockIsTranslucent(world, x - 1, y + 1, z + 1);
            isTranslucents[5] = blockIsTranslucent(world, x - 1, y + 1, z - 1);
            isTranslucents[6] = blockIsTranslucent(world, x - 1, y - 1, z + 1);
            isTranslucents[7] = blockIsTranslucent(world, x - 1, y - 1, z - 1);

            for(boolean isTranslucent : isTranslucents) {
                if(isTranslucent) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private boolean blockIsTranslucent(WorldClient world, int x, int y, int z) {
        final IBlockState blockState = world.getBlockState(new BlockPos(x, y, z));
        final Block block = blockState.getBlock();
        return block == Blocks.water ||
               block == Blocks.flowing_water ||
               block == Blocks.lava ||
               block == Blocks.flowing_lava ||
               block.isTranslucent();
    }

    private void renderBlock(int x, int y, int z, XrayBlocks block) {
        GL11.glColor4ub((byte) block.r, (byte) block.g, (byte) block.b, (byte) block.a);

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

    @SubscribeEvent
    public void keyboardEvent(KeyInputEvent key) {
        if (this.toggleXrayBinding.isPressed()) {
            toggleXray = !toggleXray;
            if (toggleXray) {
                reloadConfig();
                cooldownTicks = 0;
            } else {
                GL11.glDeleteLists(displayListid, 1);
            }
        }
    }
    
    private void reloadConfig() {
        config.load();
        radius = config.get("Xray", "Radius", 45, "Radius for X-ray").getInt();
        antiAntiXrayLevel = config.get("Xray", "AntiAntiXrayLevel", 0, "Anti Anti X-ray Level (0: off, 1~3: open)").getInt();
        if(antiAntiXrayLevel > 3 || antiAntiXrayLevel < 0) {
            antiAntiXrayLevel = 0;
        }
        XrayBlocks.load(config);
        config.save();
    }

    @SubscribeEvent
    public void renderWorldLastEvent(RenderWorldLastEvent evt) {
        if ((!toggleXray) || (this.mc.theWorld == null)) {
            return;
        }

        double doubleX = this.mc.thePlayer.lastTickPosX + (this.mc.thePlayer.posX - this.mc.thePlayer.lastTickPosX) * evt.partialTicks;
        double doubleY = this.mc.thePlayer.lastTickPosY + (this.mc.thePlayer.posY - this.mc.thePlayer.lastTickPosY) * evt.partialTicks;
        double doubleZ = this.mc.thePlayer.lastTickPosZ + (this.mc.thePlayer.posZ - this.mc.thePlayer.lastTickPosZ) * evt.partialTicks;

        GL11.glPushMatrix();
        GL11.glTranslated(-doubleX, -doubleY, -doubleZ);
        GL11.glCallList(displayListid);
        GL11.glPopMatrix();
    }
}
