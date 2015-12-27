package org.cat73.cheats.mods;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.lwjgl.input.Keyboard;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModInfo {
    String name();
    String description() default "";
    boolean showInGui() default true;
    int defaultHotkey() default Keyboard.KEY_NONE;
}
