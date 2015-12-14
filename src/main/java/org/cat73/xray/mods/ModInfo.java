package org.cat73.xray.mods;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.lwjgl.input.Keyboard;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModInfo {
    String name();
    String description() default "";
    int hotkey() default Keyboard.KEY_NONE;
}
