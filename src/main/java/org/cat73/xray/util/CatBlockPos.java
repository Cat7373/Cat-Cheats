package org.cat73.xray.util;

import net.minecraft.util.BlockPos;

public class CatBlockPos extends BlockPos {
    private int x;
    private int y;
    private int z;

    public CatBlockPos() {
        super(0, 0, 0);
    }

    public void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
}
