package org.cat73.cheats.hideself.asm;

import net.minecraft.launchwrapper.IClassTransformer;

public class CatCheatsTransformer implements IClassTransformer {
    @Override
    public byte[] transform(final String name, final String transformedName, final byte[] basicClass) {
        return basicClass;
    }
}
