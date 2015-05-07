/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api;

import java.util.logging.Level;
import java.util.logging.Logger;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Log {
    public enum NoteLevel {
        INFO, WARNING, SEVERE;
    }
    
    public static void error(String message) {
        Validate.notNull(message, "message can not be null");
        log(Level.WARNING, "Blad: " + message);
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            if (player.isStaff()) {
                player.sendMessage(Color.RED + "[" + Color.DARK_RED + "Blad" + Color.RED + "] " + Color.YELLOW + message);
            }
        }
    }
    
    public static void infoAdmins(String message) {
        Validate.notNull(message, "message can not be null");
        log(Level.INFO, message);
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            if (player.isStaff()) {
                player.sendMessage(Color.DARK_AQUA + "[" + Color.AQUA + "INFO" + Color.DARK_AQUA + "] " + Color.YELLOW + message);
            }
        }
    }
    
    public static void log(Level level, String message) {
        Validate.notNull(level, "level can not be null");
        Validate.notNull(message, "message can not be null");
        message = "(Arcade) " + fixColors(message);
        Logger.getLogger("arcade").log(level, message);
    }
    
    public static void noteAdmins(String message) {
        Validate.notNull(message, "message can not be null");
        noteAdmins(message, NoteLevel.INFO);
    }
    
    public static void noteAdmins(String message, NoteLevel level) {
        Validate.notNull(message, "message can not be null");
        Validate.notNull(level, "level can not be null");
        log(Level.INFO, "[Notify - " + level.toString() + "] " + message);
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            if (player.hasPermission(getPermissionByLevel(level))) {
                player.sendMessage(getColorByLevel(level) + " " + message);
            }
        }
    }
    
    private static String fixColors(String message) {
        StringBuilder builder = new StringBuilder();
        if (message.contains(String.valueOf(Color.SECTION_SIGN))) { // We need to remove colors from this message
            boolean startsWith = message.startsWith(String.valueOf(Color.SECTION_SIGN));
            String[] split = message.split(String.valueOf(Color.SECTION_SIGN));
            for (int i = 0; i < split.length; i++) {
                String label = split[i];
                if (!startsWith && i == 0) {
                    builder.append(label);
                } else if (label.length() > 0) {
                    builder.append(label.substring(1));
                }
            }
        } else {
            builder.append(message);
        }
        return builder.toString();
    }
    
    private static String getColorByLevel(NoteLevel level) {
        switch (level) {
            case WARNING: return Color.DARK_RED + "[WARN]";
            case SEVERE: return Color.DARK_RED + Color.UNDERLINE + "[ERROR]";
            default: return Color.RED + "[INFO]";
        }
    }
    
    private static String getPermissionByLevel(NoteLevel level) {
        Validate.notNull(level, "level can not be null");
        switch (level) {
            case INFO: return "arcade.notify.info";
            case WARNING: return "arcade.notify.warn";
            case SEVERE: return "arcade.notify.severe";
        }
        return null;
    }
}
