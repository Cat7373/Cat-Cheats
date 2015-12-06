package org.cat73.xray;

import java.util.ArrayList;

import net.minecraftforge.common.config.Configuration;

public class XrayBlocks {
    public static String[] defaultBlocks = new String[]{
        "0 0 128 200 -1 minecraft:lapis_ore",
        "255 0 0 200 -1 minecraft:redstone_ore",
        "255 255 0 200 -1 minecraft:gold_ore",
        "0 255 0 200 -1 minecraft:emerald_ore",
        "0 191 255 200 -1 minecraft:diamond_ore"
    };
    public static ArrayList<XrayBlocks> blocks = new ArrayList<XrayBlocks>();

    public int r;
    public int g;
    public int b;
    public int a;
    public int meta;
    public String name = "";

    private XrayBlocks() {}

    private static XrayBlocks fromString(String s) {
        XrayBlocks result = new XrayBlocks();
        String[] info = s.split(" ");
        result.r = Integer.parseInt(info[0]);
        result.g = Integer.parseInt(info[1]);
        result.b = Integer.parseInt(info[2]);
        result.a = Integer.parseInt(info[3]);
        result.meta = Integer.parseInt(info[4]);
        result.name = info[5];
        return result;
    }

    public static void load(Configuration config) {
        String[] configBlocksList = config.getStringList("Blocks", "Xray", defaultBlocks, "Blocks for X-ray");
        blocks.clear();
        for(String configBlock : configBlocksList) {
            blocks.add(XrayBlocks.fromString(configBlock));
        }
    }
}
