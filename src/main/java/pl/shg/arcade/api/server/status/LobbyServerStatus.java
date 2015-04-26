/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server.status;

import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public class LobbyServerStatus implements ServerStatus {
    @Override
    public boolean enabled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String canConnect(Player player) {
        throw new UnsupportedOperationException("Not supported yet.");
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
}
