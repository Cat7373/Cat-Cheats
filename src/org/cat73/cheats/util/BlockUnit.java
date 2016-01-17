package org.cat73.cheats.util;

import cpw.mods.fml.common.registry.FMLControlledNamespacedRegistry;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;

public class BlockUnit {
    public final static FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.blockRegistry;
    
    public static String getNameById(int id) {
        final Block block = blockRegistery.getObjectById(id);
        return blockRegistery.getNameForObject(block).toString();
    }
}
