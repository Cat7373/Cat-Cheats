package org.cat73.cheats.config;

import java.util.ArrayList;

import org.cat73.cheats.util.BlockUnit;
import org.cat73.cheats.util.PlayerMessage;

import net.minecraft.block.Block;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;

public class XrayBlock {
    private final static FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.getBlockRegistry();
    private final static String[] defaultBlocks = new String[] {
        "minecraft:lapis_ore -1 0 0 -128 -56",
        "minecraft:redstone_ore -1 -1 0 0 -56",
        "minecraft:gold_ore -1 -1 -1 0 -56",
        "minecraft:emerald_ore -1 0 -1 0 -56",
        "minecraft:diamond_ore -1 0 -65 -1 -56"
    };
    private final static ArrayList<XrayBlock> blocks = new ArrayList<XrayBlock>();

    public final int id;
    public final byte damage;
    public final byte r;
    public final byte g;
    public final byte b;
    public final byte a;

    public XrayBlock(final int id, final byte damage, final byte r, final byte g, final byte b, final byte a) {
        this.id = id;
        this.damage = damage;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public String toString() {
        return BlockUnit.getNameById(this.id) + " " + this.damage + " " + this.r + " " + this.g + " " + this.b + " " + this.a;
    }

    public static void load() {
        blocks.clear();

        final String[] configBlocksList = Config.instance().config.get("Cheats", "xray.blocks", defaultBlocks, "").getStringList();

        XrayBlock block;
        for(final String configBlock : configBlocksList) {
            try {
                block = XrayBlock.fromString(configBlock);
                if(block != null) {
                    add(block);
                }
            } catch(Exception e) {
                PlayerMessage.warn("Load xray block info fali!");
                PlayerMessage.warn(configBlock);
            }
        }
    }
    
    public static XrayBlock fromString(final String s) {
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

    public static void save() {
        final Property configBolcks = Config.instance().config.get("Cheats", "xray.blocks", defaultBlocks, "");
        final String[] configBlocksList = new String[getSize()];
        for(int i = 0; i < configBlocksList.length; i++) {
            configBlocksList[i] = blocks.get(i).toString();
        }
        configBolcks.set(configBlocksList);
        Config.instance().save();
    }

    public static XrayBlock find(final int id, final byte damage) {
        for(final XrayBlock xrayBlock : blocks) {
            if(xrayBlock.id == id && xrayBlock.damage == damage) {
                return xrayBlock;
            }
        }
        if(damage != -1) {
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
        // TODO meta -1 跟 非 -1 的分开存 优化性能
        // TODO 自动去重
        blocks.set(index, block);
    }
    
    public static void add(final XrayBlock block) {
        // TODO meta -1 跟 非 -1 的分开存 优化性能
        // TODO 自动去重
        blocks.add(block);
    }
}
