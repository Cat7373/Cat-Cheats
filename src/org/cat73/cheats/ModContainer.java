package org.cat73.cheats;

import java.io.File;
import java.util.List;

import org.cat73.cheats.hideself.asm.FMLCorePlugin;
import org.cat73.cheats.reference.Reference;

import com.google.common.collect.ImmutableList;
import com.google.common.eventbus.EventBus;

import net.minecraftforge.fml.client.FMLFileResourcePack;
import net.minecraftforge.fml.client.FMLFolderResourcePack;
import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.MetadataCollection;

public class ModContainer extends DummyModContainer {
    public ModContainer() {
        super(MetadataCollection.from(MetadataCollection.class.getResourceAsStream("/mcmod.info"), Reference.MODID).getMetadataForId(Reference.MODID, null));
    }

    @Override
    public Class<?> getCustomResourcePackClass() {
        if (this.getSource().isDirectory()) {
            return FMLFolderResourcePack.class;
        } else {
            return FMLFileResourcePack.class;
        }
    }

    @Override
    public String getGuiClassName() {
        return null;
    }

    @Override
    public List<String> getOwnedPackages() {
        return ImmutableList.of("org.cat73.cheats");
    }

    @Override
    public File getSource() {
        return FMLCorePlugin.location;
    }

    @Override
    public boolean registerBus(final EventBus bus, final LoadController controller) {
        bus.register(this);
        return true;
    }
}
