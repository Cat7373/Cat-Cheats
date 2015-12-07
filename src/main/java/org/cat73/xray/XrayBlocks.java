package org.cat73.xray;

import java.util.ArrayList;

import net.minecraftforge.common.config.Configuration;

public class XrayBlocks {
    public static final String[] defaultBlocks = new String[]{
        "0 0 128 200 -1 minecraft:lapis_ore",
        "255 0 0 200 -1 minecraft:redstone_ore",
        "255 255 0 200 -1 minecraft:gold_ore",
        "0 255 0 200 -1 minecraft:emerald_ore",
        "0 191 255 200 -1 minecraft:diamond_ore"
    };
    public static final ArrayList<XrayBlocks> blocks = new ArrayList<XrayBlocks>();

    public byte r;
    public byte g;
    public byte b;
    public byte a;
    public byte meta;
    public String name;

    private XrayBlocks() {}

    private static XrayBlocks fromString(final String s) {
        final XrayBlocks result = new XrayBlocks();
        final String[] info = s.split(" ");
        result.name = info[0];
        result.meta = Byte.parseByte(info[1]);
        result.r = (byte) Integer.parseInt(info[2]);
        result.g = (byte) Integer.parseInt(info[3]);
        result.b = (byte) Integer.parseInt(info[4]);
        result.a = (byte) Integer.parseInt(info[5]);
        return result;
    }

    public static void load(final Configuration config) {
        final String[] configBlocksList = config.getStringList("Blocks", "Xray", defaultBlocks, "Blocks for X-ray");
        blocks.clear();
        for(final String configBlock : configBlocksList) {
            blocks.add(XrayBlocks.fromString(configBlock));
        }
    }
}
