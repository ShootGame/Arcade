/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api;

import pl.shg.arcade.api.server.Server;
import pl.shg.arcade.api.command.CommandManager;
import pl.shg.arcade.api.server.ServerManager;
import pl.shg.arcade.api.map.MapManager;
import pl.shg.arcade.api.match.MatchManager;
import pl.shg.arcade.api.map.team.TeamManager;
import pl.shg.arcade.api.module.ModuleManager;
import pl.shg.arcade.api.permissions.PermissionsManager;
import pl.shg.arcade.api.server.ProxyServer;

/**
 *
 * @author Aleksander
 */
public interface Plugin {
    CommandManager getCommands();
    
    MapManager getMaps();
    
    MatchManager getMatches();
    
    ModuleManager getModules();
    
    ArcadeOptions getOptions();
    
    PermissionsManager getPermissions();
    
    PlayerManagement getPlayerManagement();
    
    Plugin getPlugin();
    
    ProxyServer getProxy();
    
    Server getServer();
    
    ServerManager getServers();
    
    String getSettingsDirectory();
    
    TeamManager getTeams();
}
