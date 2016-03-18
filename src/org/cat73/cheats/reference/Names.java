package org.cat73.cheats.reference;

public class Names {
    public static final class Gui {
        public static final String ENABLE = "catcheats.gui.enable";
        public static final String DISABLE = "catcheats.gui.disable";
        public static final String TOGGLE = "catcheats.gui.toggle";
        public static final String SETTING = "catcheats.gui.setting";
        public static final String SETHOTKEY = "catcheats.gui.sethotkey";
        public static final String EXIT = "catcheats.gui.exit";
    }

    public static final class Keys {
        public static final String GUI = "catcheats.keys.gui";
    }

    public static final class Mods {
        public static final class BlockXray {
            public static final class Gui {
                public static final class AddBlock {
                    public static final String SAVE = "catcheats.mods.blockxray.gui.addblock.save";
                    public static final String CANCEL = "catcheats.mods.blockxray.gui.addblock.cancel";
                    public static final String BLOCKID = "catcheats.mods.blockxray.gui.addblock.blockid";
                    public static final String DAMAGE = "catcheats.mods.blockxray.gui.addblock.damage";
                    public static final String R = "catcheats.mods.blockxray.gui.addblock.r";
                    public static final String G = "catcheats.mods.blockxray.gui.addblock.g";
                    public static final String B = "catcheats.mods.blockxray.gui.addblock.b";
                    public static final String A = "catcheats.mods.blockxray.gui.addblock.a";
                }

                public static final class Config {
                    public static final String SAVE = "catcheats.mods.blockxray.gui.config.save";
                    public static final String CANCEL = "catcheats.mods.blockxray.gui.config.cancel";
                    public static final String RADIUS = "catcheats.mods.blockxray.gui.config.radius";
                    public static final String INTERVAL = "catcheats.mods.blockxray.gui.config.interval";
                    public static final String ANTIANTIXRAY = "catcheats.mods.blockxray.gui.config.antiantixray";
                }

                public static final class Main {
                    public static final String ADDBLOCK = "catcheats.mods.blockxray.gui.main.addblock";
                    public static final String EDITBLOCK = "catcheats.mods.blockxray.gui.main.editblock";
                    public static final String DELETEBLOCK = "catcheats.mods.blockxray.gui.main.deleteblock";
                    public static final String CONFIG = "catcheats.mods.blockxray.gui.main.config";
                    public static final String EXIT = "catcheats.mods.blockxray.gui.main.exit";
                }
            }
        }

        public static final class CreateGive {
            public static final class Error {
                public static final String ITEM_NOT_FOUND = "commands.give.notFound";
                public static final String NBT_ERROR = "commands.give.tagError";
                public static final String MISSING_PARAM = "commands.give.usage";
                public static final String UNKNOW_ITEM_NAME = "catcheats.mods.creategive.error.unknow_item_name";
                public static final String COUNT_ERROR = "catcheats.mods.creategive.error.count_error";
                public static final String DAMAGE_ERROR = "catcheats.mods.creategive.error.damage_error";
                public static final String UNKNOW_ERROR = "catcheats.mods.creategive.error.unknow_error";
            }

            public static final class Gui {
                public static final String EXECUTE = "catcheats.mods.creategive.gui.execute";
                public static final String EXIT = "catcheats.mods.creategive.gui.exit";
                public static final String SLOT = "catcheats.mods.creategive.gui.slot";
                public static final String COMMAND = "catcheats.mods.creategive.gui.command";
            }
        }
    }
}
