/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class Log {
    private static Debugger debugger;
    
    public enum NoteLevel {
        INFO, WARNING, SEVERE;
    }
    
    public static void debug(String message) {
        debug(null, message);
    }
    
    public static void debug(Level level, String message) {
        Validate.notNull(message);
        if (getDebugger() != null) {
            getDebugger().debug(level, message);
        }
    }
    
    public static void error(String message) {
        Validate.notNull(message);
        log(Level.WARNING, "Blad: " + message);
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            if (player.isStaff()) {
                player.sendMessage(Color.RED + "[" + Color.DARK_RED + "Blad" + Color.RED + "] " + Color.YELLOW + message);
            }
        }
    }
    
    public static Debugger getDebugger() {
        return debugger;
    }
    
    public static void infoAdmins(String message) {
        Validate.notNull(message);
        log(Level.INFO, message);
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            if (player.isStaff()) {
                player.sendMessage(Color.DARK_AQUA + "[" + Color.AQUA + "INFO" + Color.DARK_AQUA + "] " + Color.YELLOW + message);
            }
        }
    }
    
    public static void log(Level level, String message) {
        log(level, message, null);
    }
    
    public static void log(Level level, String message, Throwable throwable) {
        Validate.notNull(level);
        if (message == null) {
            message = throwable.getMessage();
        }
        
        message = "(Arcade) " + Color.fixColors(message);
        Logger.getLogger(Log.class.getName()).log(level, message, throwable);
        
        if (level.equals(Level.SEVERE) || level.equals(Level.WARNING)) {
            error(message);
        }
    }
    
    public static void noteAdmins(String message) {
        Validate.notNull(message);
        noteAdmins(message, NoteLevel.INFO);
    }
    
    public static void noteAdmins(String message, NoteLevel level) {
        Validate.notNull(message);
        Validate.notNull(level);
        log(Level.INFO, "[Notify - " + level.toString() + "] " + message);
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            if (player.hasPermission(getPermissionByLevel(level))) {
                player.sendMessage(getColorByLevel(level) + " " + message);
            }
        }
    }
    
    public static void setDebugger(Debugger debugger) {
        Log.debugger = debugger;
    }
    
    private static String getColorByLevel(NoteLevel level) {
        switch (level) {
            case WARNING: return Color.DARK_RED + "[WARN]";
            case SEVERE: return Color.DARK_RED + Color.UNDERLINE + "[ERROR]";
            default: return Color.RED + "[INFO]";
        }
    }
    
    private static String getPermissionByLevel(NoteLevel level) {
        Validate.notNull(level);
        switch (level) {
            case INFO: return "arcade.notify.info";
            case WARNING: return "arcade.notify.warn";
            case SEVERE: return "arcade.notify.severe";
        }
        return null;
    }
}
