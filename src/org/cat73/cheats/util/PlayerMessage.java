package org.cat73.cheats.util;

import org.apache.logging.log4j.Level;
import org.cat73.cheats.reference.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.FMLLog;

/**
 * 玩家信息类
 * 
 * @author Cat73
 */
public class PlayerMessage {
    private static final Minecraft mc = Minecraft.getMinecraft();

    /**
     * 向玩家输出格式化的调试信息
     * 
     * @param format 要输出的信息格式
     * @param args 格式化时使用的数据列表
     */
    public static void debug(final String format, final Object... args) {
        if (Reference.DEBUG) {
            FMLLog.log(Level.DEBUG, format, args);
            PlayerMessage.message("[调试]" + format, args);
        }
    }

    /**
     * 向玩家输出调试信息
     * 
     * @param objs 要输出的数据列表
     */
    public static void debugs(final Object... objs) {
        if (Reference.DEBUG) {
            String message = "";
            for (final Object obj : objs) {
                message += obj.toString();
                message += ", ";
            }
            message = message.substring(0, message.length() - 2);

            FMLLog.log(Level.DEBUG, message);
            PlayerMessage.message("[调试]" + message);
        }
    }

    /**
     * 向玩家输出错误
     * 
     * @param format 要输出的信息格式
     * @param args 格式化时使用的数据列表
     */
    public static void error(final String format, final Object... args) {
        FMLLog.log(Level.ERROR, format, args);
        PlayerMessage.message("[错误]" + format, args);
    }

    /**
     * 向玩家输出信息
     * 
     * @param format 要输出的信息格式
     * @param args 格式化时使用的数据列表
     */
    public static void info(final String format, final Object... args) {
        FMLLog.info(format, args);
        PlayerMessage.message("[信息]" + format, args);
    }

    /**
     * 给玩家发送一条消息
     * 
     * @param format 要发送的信息格式
     * @param args 格式化时使用的数据列表
     */
    public static void message(final String format, final Object... args) {
        final String message = String.format(format, args);
        if (PlayerMessage.mc.thePlayer != null) {
            PlayerMessage.mc.thePlayer.addChatMessage(new TextComponentTranslation(message));
        }
    }

    /**
     * 向玩家输出警告
     * 
     * @param format 要输出的信息格式
     * @param args 格式化时使用的数据列表
     */
    public static void warn(final String format, final Object... args) {
        FMLLog.warning(format, args);
        PlayerMessage.message("[警告]" + format, args);
    }

    /**
     * 向玩家输出警告
     * 
     * @param format 要输出的信息格式
     * @param args 格式化时使用的数据列表
     */
    public static void warning(final String format, final Object... args) {
        PlayerMessage.warn(format, args);
    }
}
