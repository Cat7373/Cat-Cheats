package org.cat73.cheats.mods;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.lwjgl.input.Keyboard;

/**
 * Mod 信息
 * 
 * @author Cat73
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface ModInfo {
    /** 这个 Mod 的默认开关热键 */
    int defaultHotkey() default Keyboard.KEY_NONE;

    /** Mod 的描述 */
    String description() default "";

    /** Mod 的名称 */
    String name();

    /** 这个 Mod 是否显示在 Gui 中 */
    boolean showInGui() default true;
}
