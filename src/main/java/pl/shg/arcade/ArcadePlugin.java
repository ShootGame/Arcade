/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade;

import java.io.File;
import pl.shg.arcade.api.ArcadeOptions;
import pl.shg.arcade.api.PlayerManagement;
import pl.shg.arcade.api.Plugin;
import pl.shg.arcade.api.PluginProperties;
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
public class ArcadePlugin implements Plugin {
    private final CommandManager commands;
    private final MapManager maps;
    private final MatchManager matches;
    private final ModuleManager modules;
    private final ArcadeOptions options;
    private final PermissionsManager permissions;
    private final PlayerManagement playerManagement;
    private Plugin plugin;
    private final ProxyServer proxy;
    private final Server server;
    private final String settings;
    private final TeamManager teams;
    
    public ArcadePlugin(Server server, PluginProperties properties) {
        Validate.notNull(server, "server can not be null");
        Validate.notNull(properties, "properties can not be null");
        this.options = new ArcadeOptions();
        this.setInstance(); // getPlugin() method
        this.server = server;
        
        this.commands = properties.getCommands();
        this.maps = new ArcadeMapManager(properties.getConfiguration(), new File(properties.getMapsDirectory()));
        this.matches = new ArcadeMatchManager();
        this.modules = new ArcadeModuleManager();
        this.permissions = properties.getPermissions();
        this.playerManagement = properties.getPlayerManagement();
        this.proxy = properties.getProxyServer();
        this.settings = properties.getSettingsDirectory();
        this.teams = new ArcadeTeamManager();
        
        this.arcadeSetup();
    }
    
    @Override
    public CommandManager getCommands() {
        return this.commands;
    }
    
    @Override
    public MapManager getMaps() {
        return this.maps;
    }
    
    @Override
    public MatchManager getMatches() {
        return this.matches;
    }
    
    @Override
    public ModuleManager getModules() {
        return this.modules;
    }
    
    @Override
    public ArcadeOptions getOptions() {
        return this.options;
    }
    
    @Override
    public PermissionsManager getPermissions() {
        return this.permissions;
    }
    
    @Override
    public PlayerManagement getPlayerManagement() {
        return this.playerManagement;
    }
    
    @Override
    public Plugin getPlugin() {
        return this.plugin;
    }
    
    @Override
    public ProxyServer getProxy() {
        return this.proxy;
    }
    
    @Override
    public Server getServer() {
        return this.server;
    }
    
    @Override
    public String getSettingsDirectory() {
        return this.settings;
    }
    
    @Override
    public TeamManager getTeams() {
        return this.teams;
    }
    
    private void arcadeSetup() {
        this.getPermissions().loadPermissions(this.getOptions().getGlobalPermissionsURL());
    }
    
    private void setInstance() {
        this.plugin = this;
    }
}
