package org.cat73.xray.config;

import java.util.ArrayList;

import org.cat73.xray.util.PlayerMessage;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;

public class XrayBlock {
    private final static FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.getBlockRegistry();
    private final static String[] defaultBlocks = new String[] {
        "minecraft:lapis_ore -1 0 0 128 200",
        "minecraft:redstone_ore -1 255 0 0 200",
        "minecraft:gold_ore -1 255 255 0 200",
        "minecraft:emerald_ore -1 0 255 0 200",
        "minecraft:diamond_ore -1 0 191 255 200"
    };
    private final static ArrayList<XrayBlock> blocks = new ArrayList<XrayBlock>();

    public final int id;
    public final byte meta;
    public final byte r;
    public final byte g;
    public final byte b;
    public final byte a;

    private XrayBlock(final int id, final byte meta, final byte r, final byte g, final byte b, final byte a) {
        this.id = id;
        this.meta = meta;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public String toString() {
        // TODO 一些诸如方块id转名称的方法放进util包里
        final Block block = blockRegistery.getObjectById(this.id);
        return blockRegistery.getNameForObject(block).toString() + " " + this.meta + " " + this.r + " " + this.g + " " + this.b + " " + this.a;
    }

    public static XrayBlock fromString(final String s) {
        // TODO 自动去重
        final String[] info = s.split(" ");

        final int id = blockRegistery.getId(info[0]);
        if(id == -1) {
            PlayerMessage.warn("Block " + info[0] + " not found!");
            return null;
        }
        final byte meta = Byte.parseByte(info[1]);
        final byte r = Byte.parseByte(info[2]);
        final byte g = Byte.parseByte(info[3]);
        final byte b = Byte.parseByte(info[4]);
        final byte a = Byte.parseByte(info[5]);

        return new XrayBlock(id, meta, r, g, b, a);
    }

    public static void load() {
        blocks.clear();

        final String[] configBlocksList = Config.config.get("Xray", "Blocks", defaultBlocks, "Blocks for X-ray").getStringList();

        XrayBlock block;
        for(final String configBlock : configBlocksList) {
            try {
                // TODO meta -1 跟 非 -1 的分开存 优化性能
                block = XrayBlock.fromString(configBlock);
                if(block != null) {
                    blocks.add(block);
                }
            } catch(Exception e) {
                PlayerMessage.warn("Load xray block info fali!");
                PlayerMessage.warn(configBlock);
            }
        }
    }

    public static void save() {
        final Property configBolcks = Config.config.get("Xray", "Blocks", defaultBlocks, "Blocks for X-ray");
        final String[] configBlocksList = new String[getSize()];
        for(int i = 0; i < configBlocksList.length; i++) {
            configBlocksList[i] = blocks.get(i).toString();
        }
        configBolcks.set(configBlocksList);
        Config.config.save();
    }

    public static XrayBlock find(final int id, final byte meta) {
        for(final XrayBlock xrayBlock : blocks) {
            if(xrayBlock.id == id && xrayBlock.meta == meta) {
                return xrayBlock;
            }
        }
        if(meta != -1) {
            return find(id, (byte) -1);
        }
        return null;
    }

    public static int getSize() {
        return blocks.size();
    }

    public static XrayBlock getByIndex(final int index) {
        return blocks.get(index);
    }

    public static void delByIndex(final int index) {
        // TODO 为空时自动替换为默认值
        blocks.remove(index);
    }

    public static void set(final XrayBlock block, final int index) {
        blocks.set(index, block);
    }
}
