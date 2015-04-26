/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.server;

import java.util.List;

/**
 *
 * @author Aleksander
 */
public interface ServerManager {
    void addServer(ArcadeServer server);
    
    ArcadeServer getCurrentServer();
    
    ArcadeServer getLobbyServer();
    
    ArcadeServer getServer(String name);
    
    List<ArcadeServer> getServers();
    
    void setCurrentServer(ArcadeServer current);
}
