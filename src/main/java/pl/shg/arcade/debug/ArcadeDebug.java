/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.debug;

import java.util.Scanner;
import java.util.UUID;
import pl.shg.arcade.ArcadeFactory;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.PluginProperties;
import pl.shg.arcade.api.command.ConsoleSender;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.server.Role;
import pl.shg.arcade.api.server.Server;
import pl.shg.arcade.api.server.development.TestCommand;
import pl.shg.arcade.debug.impl.DebugCommandManager;
import pl.shg.arcade.debug.impl.DebugConfiguration;
import pl.shg.arcade.debug.impl.DebugPermissionsManager;
import pl.shg.arcade.debug.impl.DebugPlayer;
import pl.shg.arcade.debug.impl.DebugPlayerManagement;
import pl.shg.arcade.debug.impl.DebugProxyServer;
import pl.shg.arcade.debug.impl.DebugServer;

/**
 *
 * @author Aleksander
 */
public final class ArcadeDebug {
    private static DebugServer server;
    
    public static void main(String[] args) {
        server = new DebugServer(Runtime.getRuntime());
        
        PluginProperties properties = new PluginProperties();
        properties.setCommands(new DebugCommandManager());
        properties.setConfiguration(new DebugConfiguration());
        properties.setMapsDirectory("");
        properties.setPermissions(new DebugPermissionsManager());
        properties.setPlayerManagement(new DebugPlayerManagement());
        properties.setProxyServer(new DebugProxyServer());
        properties.setSettingsDirectory("");
        
        Arcade.setPlugin(ArcadeFactory.newInstance(server, properties));
        
        Role.DEVELOPMENT.getRole().onServerEnable();
        
        TestCommand.registerDefaults();
        DebugCommandManager.registerDebugCommands();
        
        debugPlayers();
        
        listenToCommands();
    }
    
    public static ConsoleSender getConsole() {
        return Arcade.getCommands().getConsoleSender();
    }
    
    public static DebugServer getServer() {
        return server;
    }
    
    private static void debugPlayers() {
        for (int i = 0; i < 5; i++) {
            Player player = new DebugPlayer("Player-" + (i + 1), UUID.randomUUID());
            server.addPlayer(player);
        }
    }
    
    private static void listenToCommands() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            
            String args[] = line.split(" ");
            DebugCommandManager.perform(getConsole(), args);
        }
    }
}
