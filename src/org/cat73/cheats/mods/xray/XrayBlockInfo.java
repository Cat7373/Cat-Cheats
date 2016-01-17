package org.cat73.cheats.mods.xray;

import org.cat73.cheats.config.XrayBlock;

public class XrayBlockInfo {
    public final int x;
    public final int y;
    public final int z;
    public final XrayBlock xrayBlock;

    public XrayBlockInfo(final int x, final int y, final int z, final XrayBlock xrayBlock) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.xrayBlock = xrayBlock;
    }
}
