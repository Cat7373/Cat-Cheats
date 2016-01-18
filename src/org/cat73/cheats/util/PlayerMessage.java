package org.cat73.cheats.util;

import org.apache.logging.log4j.Level;
import org.cat73.cheats.reference.Reference;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

/**
 * 玩家信息类
 * @author Cat73
 */
public class PlayerMessage {
    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * 给玩家发送一条消息
     * @param message 要发送的消息
     */
    public static void message(final String message) {
        if(message != null && !message.trim().isEmpty()) {
            if(mc.thePlayer != null) {
                mc.thePlayer.addChatMessage(new ChatComponentText(message));
            }
        }
    }

    /**
     * 向玩家输出信息
     * @param message 要输出的信息
     */
    public static void info(final String message) {
        FMLLog.info(message);
        message("[信息]" + message);
    }

    /**
     * 向玩家输出警告
     * @param message 要输出的信息
     */
    public static void warning(final String message) {
        warn(message);
    }

    /**
     * 向玩家输出警告
     * @param message 要输出的信息
     */
    public static void warn(final String message) {
        FMLLog.warning(message);
        message("[警告]" + message);
    }

    /**
     * 向玩家输出错误
     * @param message 要输出的信息
     */
    public static void error(final String message) {
        FMLLog.log(Level.ERROR, message);
        message("[错误]" + message);
    }

    /**
     * 向玩家输出调试信息
     * @param objs 要输出的数据列表
     */
    public static void debug(final Object... objs) {
        if(Reference.DEBUG) {
            String message = "";
            for(Object obj : objs) {
                message += obj.toString();
                message += ", ";
            }
            message = message.substring(0, message.length() - 2);

            FMLLog.log(Level.DEBUG, message);
            message("[调试]" + message);
        }
    }
    
    /**
     * 向玩家输出格式化的调试信息
     * @param format 要输出的信息格式
     * @param args 格式化时使用的数据列表
     */
    public static void debugFormat(final String format, final Object... args) {
        if(Reference.DEBUG) {
            String message = String.format(format, args);

            FMLLog.log(Level.DEBUG, message);
            message("[调试]" + message);
        }
    }
}
