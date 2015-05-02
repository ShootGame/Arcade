/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api;

import pl.shg.arcade.api.server.Server;
import pl.shg.arcade.api.command.CommandManager;
import pl.shg.arcade.api.map.MapManager;
import pl.shg.arcade.api.match.MatchManager;
import pl.shg.arcade.api.map.team.TeamManager;
import pl.shg.arcade.api.module.ModuleManager;
import pl.shg.arcade.api.permissions.PermissionsManager;
import pl.shg.arcade.api.server.ProxyServer;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public final class Arcade {
    private static Plugin plugin;
    
    public static CommandManager getCommands() {
        return getPlugin().getCommands();
    }
    
    public static MapManager getMaps() {
        return getPlugin().getMaps();
    }
    
    public static MatchManager getMatches() {
        return getPlugin().getMatches();
    }
    
    public static ModuleManager getModules() {
        return getPlugin().getModules();
    }
    
    public static ArcadeOptions getOptions() {
        return getPlugin().getOptions();
    }
    
    public static PermissionsManager getPermissions() {
        return getPlugin().getPermissions();
    }
    
    public static PlayerManagement getPlayerManagement() {
        return getPlugin().getPlayerManagement();
    }
    
    public static Plugin getPlugin() {
        return plugin;
    }
    
    public static ProxyServer getProxy() {
        return getPlugin().getProxy();
    }
    
    public static Server getServer() {
        return getPlugin().getServer();
    }
    
    public static String getSettingsDirectory() {
        return getPlugin().getSettingsDirectory();
    }
    
    public static TeamManager getTeams() {
        return getPlugin().getTeams();
    }
    
    public static void setPlugin(Plugin plugin) {
        Validate.notNull(plugin, "plugin can not be null");
        if (Arcade.plugin != null) {
            throw new UnsupportedOperationException("Cannot redefine singleton plugin");
        } else {
            Arcade.plugin = plugin;
        }
    }
}
