package org.cat73.cheats.config;

import java.util.ArrayList;

import org.cat73.cheats.util.BlockUnit;
import org.cat73.cheats.util.PlayerMessage;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraftforge.common.config.Property;

public class XrayBlock {
    private final static FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.blockRegistry;
    private final static String[] defaultBlocks = new String[] {"minecraft:lapis_ore -1 0 0 -128 -56", "minecraft:redstone_ore -1 -1 0 0 -56", "minecraft:gold_ore -1 -1 -1 0 -56", "minecraft:emerald_ore -1 0 -1 0 -56", "minecraft:diamond_ore -1 0 -65 -1 -56"};
    private final static ArrayList<XrayBlock> blocks = new ArrayList<XrayBlock>();
    private final static ArrayList<XrayBlock> blocks_checkDamage = new ArrayList<XrayBlock>();
    private final static ArrayList<XrayBlock> blocks_noCheckDamage = new ArrayList<XrayBlock>();

    public static void add(final XrayBlock block) {
        XrayBlock.blocks.add(block);

        if (block.damage == -1) {
            XrayBlock.blocks_noCheckDamage.add(block);
        } else {
            XrayBlock.blocks_checkDamage.add(block);
        }
    }

    public static void delByIndex(final int index) {
        final XrayBlock block = XrayBlock.blocks.remove(index);

        if (block.damage == -1) {
            XrayBlock.blocks_noCheckDamage.remove(block);
        } else {
            XrayBlock.blocks_checkDamage.remove(block);
        }
    }

    public static XrayBlock find(final int id, final byte damage) {
        for (final XrayBlock xrayBlock : XrayBlock.blocks_checkDamage) {
            if ((xrayBlock.id == id) && (xrayBlock.damage == damage)) {
                return xrayBlock;
            }
        }

        for (final XrayBlock xrayBlock : XrayBlock.blocks_noCheckDamage) {
            if (xrayBlock.id == id) {
                return xrayBlock;
            }
        }

        return null;
    }

    public static XrayBlock fromString(final String s) {
        final String[] info = s.split(" ");

        final int id = XrayBlock.blockRegistery.getId(info[0]);
        if (id == -1) {
            PlayerMessage.warn("Block %s not found!", info[0]);
            return null;
        }
        final byte meta = Byte.parseByte(info[1]);
        final byte r = Byte.parseByte(info[2]);
        final byte g = Byte.parseByte(info[3]);
        final byte b = Byte.parseByte(info[4]);
        final byte a = Byte.parseByte(info[5]);

        return new XrayBlock(id, meta, r, g, b, a);
    }

    public static XrayBlock getByIndex(final int index) {
        return XrayBlock.blocks.get(index);
    }

    public static int getSize() {
        return XrayBlock.blocks.size();
    }

    public static void load() {
        XrayBlock.blocks.clear();
        XrayBlock.blocks_checkDamage.clear();
        XrayBlock.blocks_noCheckDamage.clear();

        final String[] configBlocksList = Config.instance().config.get("Cheats", "blockxray.blocks", XrayBlock.defaultBlocks, "").getStringList();

        XrayBlock block;
        for (final String configBlock : configBlocksList) {
            try {
                block = XrayBlock.fromString(configBlock);
                if (block != null) {
                    XrayBlock.add(block);
                }
            } catch (final Exception e) {
                PlayerMessage.warn("Load xray block info fali: %s", configBlock);
            }
        }
    }

    public static void save() {
        final Property configBolcks = Config.instance().config.get("Cheats", "blockxray.blocks", XrayBlock.defaultBlocks, "");
        final String[] configBlocksList = new String[XrayBlock.getSize()];
        for (int i = 0; i < configBlocksList.length; i++) {
            configBlocksList[i] = XrayBlock.blocks.get(i).toString();
        }
        configBolcks.set(configBlocksList);
        Config.instance().save();
    }

    public static void set(final XrayBlock block, final int index) {
        final XrayBlock oldBlock = XrayBlock.blocks.set(index, block);

        if (oldBlock.damage == -1) {
            XrayBlock.blocks_noCheckDamage.remove(oldBlock);
        } else {
            XrayBlock.blocks_checkDamage.remove(oldBlock);
        }

        if (block.damage == -1) {
            XrayBlock.blocks_noCheckDamage.add(block);
        } else {
            XrayBlock.blocks_checkDamage.add(block);
        }
    }

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
}
