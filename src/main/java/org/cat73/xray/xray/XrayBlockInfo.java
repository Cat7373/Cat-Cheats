package org.cat73.xray.xray;

public class XrayBlockInfo {
    public final int x;
    public final int y;
    public final int z;
    public final XrayBlocks xrayBlock;
    
    public XrayBlockInfo(int x, int y, int z, XrayBlocks xrayBlock) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xrayBlock = xrayBlock;
    }
}
