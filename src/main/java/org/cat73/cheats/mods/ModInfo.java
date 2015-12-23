package org.cat73.cheats.mods;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModInfo {
    String name();
    String description() default "";
    boolean showInGui() default true;
    String settingClassName() default "";
}
