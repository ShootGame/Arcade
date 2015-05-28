/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.event;

import pl.shg.arcade.api.match.Match;
import pl.shg.arcade.api.match.Winner;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class MatchEndedEvent extends Event {
    private Match match;
    private Winner winner;
    
    public MatchEndedEvent(Match match, Winner winner) {
        super(MatchEndedEvent.class);
        this.setMatch(match);
        this.setWinner(winner);
    }
    
    public Match getMatch() {
        return this.match;
    }
    
    public Winner getWinner() {
        return this.winner;
    }
    
    private void setMatch(Match match) {
        Validate.notNull(match, "match can not be null");
        this.match = match;
    }
    
    private void setWinner(Winner winner) {
        this.winner = winner;
    }
}
