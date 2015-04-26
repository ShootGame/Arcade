/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server.status;

import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.server.ArcadeServer;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class ArcadeServerStatus implements ServerStatus {
    private final ArcadeServer server;
    
    public ArcadeServerStatus(ArcadeServer server) {
        Validate.notNull(server, "server can not be null");
        this.server = server;
    }
    
    @Override
    public boolean enabled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String canConnect(Player player) {
        if (this.server.isProtected() && !player.hasPermission("arcade.protected-servers.")) {
            return "Brak dostepu do tego serwera poniewaz jest on chroniony.";
        }
        return null;
    }
    
    @Override
    public int online() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String rawMOTD() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public int slots() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public ArcadeServer getServer() {
        return this.server;
    }
}
