package org.cat73.cheats.util;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.FMLControlledNamespacedRegistry;
import net.minecraftforge.fml.common.registry.GameData;

public class BlockUnit {
    public static final FMLControlledNamespacedRegistry<Block> blockRegistery = GameData.getBlockRegistry();

    public static String getNameById(final int id) {
        final Block block = BlockUnit.blockRegistery.getObjectById(id);
        return BlockUnit.blockRegistery.getNameForObject(block).toString();
    }
}
