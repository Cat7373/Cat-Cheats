package org.cat73.cheats.util;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;

public class BlockUnit {
    public final static FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.getBlockRegistry();
    
    public static String getNameById(int id) {
        final Block block = blockRegistery.getObjectById(id);
        return blockRegistery.getNameForObject(block).toString();
    }
}
