package org.cat73.cheats.mods;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.lwjgl.input.Keyboard;
/**
 * Mod 信息
 * @author Cat73
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ModInfo {
    /** Mod 的名称 */
    String name();
    /** Mod 的描述 */
    String description() default "";
    /** 这个 Mod 是否显示在 Gui 中 */
    boolean showInGui() default true;
    /** 这个 Mod 的默认开关热键 */
    int defaultHotkey() default Keyboard.KEY_NONE;
}
