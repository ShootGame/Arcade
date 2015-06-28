/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.match;

import pl.shg.commons.server.ArcadeMatchStatus;

/**
 *
 * @author Aleksander
 */
public interface MatchManager {
    Match getMatch();
    
    ArcadeMatchStatus getStatus();
    
    void setStatus(ArcadeMatchStatus status);
    
    Winner getWinner();
    
    void startNew();
}
