package org.cat73.cheats.mods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.lwjgl.input.Keyboard;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface ModInfo {
    int defaultHotkey() default Keyboard.KEY_NONE;

    String description() default "";

    String name();

    boolean showInGui() default true;
}
