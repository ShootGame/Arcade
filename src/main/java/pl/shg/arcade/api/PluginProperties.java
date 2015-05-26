/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api;

import pl.shg.arcade.ArcadeCommandManager;
import pl.shg.arcade.api.configuration.ConfigurationTechnology;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.permissions.PermissionsManager;
import pl.shg.arcade.api.server.ProxyServer;
import pl.shg.arcade.api.util.Validate;
import pl.shg.commons.server.TargetServer;

/**
 *
 * @author Aleksander
 */
public class PluginProperties {
    private ArcadeCommandManager commands;
    private ConfigurationTechnology configuration;
    private String maps;
    private PermissionsManager permissions;
    private PlayerManagement playerManagement;
    private ProxyServer proxy;
    private String settings;
    
    public PluginProperties() {}
    
    public ArcadeCommandManager getCommands() {
        return this.commands;
    }
    
    public void setCommands(ArcadeCommandManager commands) {
        Validate.notNull(commands, "commands can not be null");
        this.commands = commands;
    }
    
    public ConfigurationTechnology getConfiguration() {
        return this.configuration;
    }
    
    public void setConfiguration(ConfigurationTechnology configuration) {
        Validate.notNull(configuration, "configuration can not be null");
        this.configuration = configuration;
    }
    
    public String getMapsDirectory() {
        return this.maps;
    }
    
    public void setMapsDirectory(String directory) {
        Validate.notNull(directory, "directory can not be null");
        this.maps = directory;
    }
    
    public PermissionsManager getPermissions() {
        return this.permissions;
    }
    
    public void setPermissions(PermissionsManager permissions) {
        Validate.notNull(permissions, "permissions can not be null");
        this.permissions = permissions;
    }
    
    public PlayerManagement getPlayerManagement() {
        return this.playerManagement;
    }
    
    public void setPlayerManagement(PlayerManagement playerManagement) {
        Validate.notNull(playerManagement, "playerManagement can not be null");
        this.playerManagement = playerManagement;
    }
    
    public ProxyServer getProxyServer() {
        if (this.proxy != null) {
            return this.proxy;
        } else {
            return new ProxyServer() {
                
                @Override
                public void connect(Player player, TargetServer server) {
                    throw new UnsupportedOperationException("Proxy server is not installed.");
                }
                
                @Override
                public String getProxyName() {
                    return null;
                }
            };
        }
    }
    
    public void setProxyServer(ProxyServer proxy) {
        this.proxy = proxy;
    }
    
    public String getSettingsDirectory() {
        return this.settings;
    }
    
    public void setSettingsDirectory(String directory) {
        Validate.notNull(directory, "directory can not be null");
        this.settings = directory;
    }
}
