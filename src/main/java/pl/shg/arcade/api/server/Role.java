/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server;

import pl.shg.arcade.api.server.community.Community;
import pl.shg.arcade.api.server.development.Development;
import pl.shg.arcade.api.server.party.Party;
import pl.shg.arcade.api.server.tournament.Tournament;

/**
 *
 * @author Aleksander
 */
public enum Role {
    COMMUNITY(new Community()),
    DEVELOPMENT(new Development()),
    PARTY(new Party()),
    TOURNAMENT(new Tournament());
    
    private IServerRole role;
    
    private Role(IServerRole role) {
        if (role != null) {
            this.role = role;
        } else {
            throw new UnsupportedOperationException("The defined role is not supported yet.");
        }
    }
    
    public String getName() {
        return this.name();
    }
    
    public IServerRole getRole() {
        return this.role;
    }
}
