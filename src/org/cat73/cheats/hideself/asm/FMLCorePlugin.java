package org.cat73.cheats.hideself.asm;

import java.io.File;
import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({"org.cat73.cheats.hideself.asm"})
public class FMLCorePlugin implements IFMLLoadingPlugin, IFMLCallHook {
    public static File location;

    @Override
    public Void call() throws Exception {
        return null;
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] {"org.cat73.cheats.hideself.asm.CatCheatsTransformer"};
    }

    @Override
    public String getModContainerClass() {
        return "org.cat73.cheats.CatCheats";
    }

    @Override
    public String getSetupClass() {
        return "org.cat73.cheats.hideself.asm.FMLCorePlugin";
    }

    @Override
    public void injectData(final Map<String, Object> data) {
        FMLCorePlugin.location = (File) data.get("coremodLocation");
        if (FMLCorePlugin.location == null) {
            FMLCorePlugin.location = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        }
    }
}
