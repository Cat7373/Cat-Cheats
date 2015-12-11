package org.cat73.xray.config;

import java.util.ArrayList;

import org.cat73.xray.util.PlayerMessage;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;

public class XrayBlock {
    private static final FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.getBlockRegistry();
    private static final String[] defaultBlocks = new String[] {
        "minecraft:lapis_ore -1 0 0 128 200",
        "minecraft:redstone_ore -1 255 0 0 200",
        "minecraft:gold_ore -1 255 255 0 200",
        "minecraft:emerald_ore -1 0 255 0 200",
        "minecraft:diamond_ore -1 0 191 255 200"
    };
    private static final ArrayList<XrayBlock> blocks = new ArrayList<XrayBlock>();

    public final int blockId;
    public final byte meta;
    public final byte r;
    public final byte g;
    public final byte b;
    public final byte a;

    private XrayBlock(int blockId, byte meta, byte r, byte g, byte b, byte a) {
        this.blockId = blockId;
        this.meta = meta;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    private static void fromString(final String s) {
        if(!s.startsWith("//")) {
            final String[] info = s.split(" ");

            final int blockId = blockRegistery.getId(info[0]);
            if(blockId == -1) {
                PlayerMessage.warn("Block " + info[0] + " not found!");
                return;
            }
            final byte meta = Byte.parseByte(info[1]);
            final byte r = (byte) Integer.parseInt(info[2]);
            final byte g = (byte) Integer.parseInt(info[3]);
            final byte b = (byte) Integer.parseInt(info[4]);
            final byte a = (byte) Integer.parseInt(info[5]);

            blocks.add(new XrayBlock(blockId, meta, r, g, b, a));
        }
    }

    public static void load(final Configuration config) {
        final String[] configBlocksList = config.getStringList("Blocks", "Xray", defaultBlocks, "Blocks for X-ray");
        blocks.clear();
        for(final String configBlock : configBlocksList) {
            try {
                XrayBlock.fromString(configBlock);
            } catch(Exception e) {
                PlayerMessage.warn("Load xray block info fali!");
                PlayerMessage.warn(configBlock);
            }
        }
    }

    public static XrayBlock find(final int blockId, final byte meta) {
        for(final XrayBlock xrayBlock : blocks) {
            if(xrayBlock.blockId == blockId && xrayBlock.meta == meta) {
                return xrayBlock;
            }
        }
        if(meta != -1) {
            return find(blockId, (byte) -1);
        }
        return null;
    }
}
