/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade;

import java.util.ArrayList;
import java.util.List;
import pl.shg.arcade.api.server.ArcadeServer;
import pl.shg.arcade.api.server.ServerManager;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class ArcadeServerManager implements ServerManager {
    private ArcadeServer current;
    private final List<ArcadeServer> servers;
    
    public ArcadeServerManager() {
        this.servers = new ArrayList<>();
    }
    
    @Override
    public void addServer(ArcadeServer server) {
        Validate.notNull(server, "server can not be null");
        this.servers.add(server);
    }
    
    @Override
    public ArcadeServer getCurrentServer() {
        return this.current;
    }
    
    @Override
    public ArcadeServer getLobbyServer() {
        return this.getServer("Lobby");
    }
    
    @Override
    public ArcadeServer getServer(String name) {
        for (ArcadeServer server : this.getServers()) {
            if (server.getName().toLowerCase().contains(name.toLowerCase())) {
                return server;
            }
        }
        return null;
    }
    
    @Override
    public List<ArcadeServer> getServers() {
        return this.servers;
    }
    
    @Override
    public void setCurrentServer(ArcadeServer current) {
        Validate.notNull(current, "current");
        if (this.current != null) {
            throw new UnsupportedOperationException("Cannot redefine singleton current");
        }
        this.current = current;
    }
}
