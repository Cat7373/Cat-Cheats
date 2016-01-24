package org.cat73.cheats.reference;

public class Names {
    public final static class Keys {
        public final static String GUI = "catcheats.keys.gui";
    }
    
    public final static class Gui {
        public final static String ENABLE = "catcheats.gui.enable";
        public final static String DISABLE = "catcheats.gui.disable";
        public final static String TOGGLE = "catcheats.gui.toggle";
        public final static String SETTING = "catcheats.gui.setting";
        public final static String SETHOTKEY = "catcheats.gui.sethotkey";
        public final static String EXIT = "catcheats.gui.exit";
    }
    
    public final static class Mods {
        public final static class BlockXray {
            public final static class Gui {
                public final static class Main {
                    public final static String ADDBLOCK = "catcheats.mods.blockxray.gui.main.addblock";
                    public final static String EDITBLOCK = "catcheats.mods.blockxray.gui.main.editblock";
                    public final static String DELETEBLOCK = "catcheats.mods.blockxray.gui.main.deleteblock";
                    public final static String CONFIG = "catcheats.mods.blockxray.gui.main.config";
                    public final static String EXIT = "catcheats.mods.blockxray.gui.main.exit";
                }
                public final static class Config {
                    public final static String SAVE = "catcheats.mods.blockxray.gui.config.save";
                    public final static String CANCEL = "catcheats.mods.blockxray.gui.config.cancel";
                    public final static String RADIUS = "catcheats.mods.blockxray.gui.config.radius";
                    public final static String INTERVAL = "catcheats.mods.blockxray.gui.config.interval";
                    public final static String ANTIANTIXRAY = "catcheats.mods.blockxray.gui.config.antiantixray";
                }
                public final static class AddBlock {
                    public final static String SAVE = "catcheats.mods.blockxray.gui.addblock.save";
                    public final static String CANCEL = "catcheats.mods.blockxray.gui.addblock.cancel";
                    public final static String BLOCKID = "catcheats.mods.blockxray.gui.addblock.blockid";
                    public final static String DAMAGE = "catcheats.mods.blockxray.gui.addblock.damage";
                    public final static String R = "catcheats.mods.blockxray.gui.addblock.r";
                    public final static String G = "catcheats.mods.blockxray.gui.addblock.g";
                    public final static String B = "catcheats.mods.blockxray.gui.addblock.b";
                    public final static String A = "catcheats.mods.blockxray.gui.addblock.a";
                }
            }
        }
        
        public final static class CreateGive {
            public final static class Error {
                public final static String ITEM_NOT_FOUND = "commands.give.notFound";
                public final static String NBT_ERROR = "commands.give.tagError";
                public final static String MISSING_PARAM = "commands.give.usage";
                public final static String UNKNOW_ITEM_NAME = "catcheats.mods.creategive.error.unknow_item_name";
                public final static String COUNT_ERROR = "catcheats.mods.creategive.error.count_error";
                public final static String DAMAGE_ERROR = "catcheats.mods.creategive.error.damage_error";
                public final static String UNKNOW_ERROR = "catcheats.mods.creategive.error.unknow_error";
            }
            public final static class Gui {
                public final static String EXECUTE = "catcheats.mods.creategive.gui.execute";
                public final static String EXIT = "catcheats.mods.creategive.gui.exit";
                public final static String SLOT = "catcheats.mods.creategive.gui.slot";
                public final static String COMMAND = "catcheats.mods.creategive.gui.command";
            }
        }
    }
}
