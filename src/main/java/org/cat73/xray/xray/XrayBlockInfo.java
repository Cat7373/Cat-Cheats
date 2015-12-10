package org.cat73.xray.xray;

import org.cat73.xray.config.XrayBlock;

public class XrayBlockInfo {
    public final int x;
    public final int y;
    public final int z;
    public final XrayBlock xrayBlock;

    public XrayBlockInfo(int x, int y, int z, XrayBlock xrayBlock) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xrayBlock = xrayBlock;
    }
}
