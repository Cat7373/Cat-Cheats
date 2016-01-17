package org.cat73.cheats.util;

public class GuiUnit {
    public static int colorToARGB(final byte r, final byte g, final byte b, final byte a) {
        return (a << 24 & 0xff000000) | (r << 16 & 0x00ff0000) | (g << 8 & 0x0000ff00) | (b & 0x000000ff);
    }
}
