/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.match;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.team.Team;

/**
 *
 * @author Aleksander
 */
public class TeamWinner implements Winner {
    private final Team team;
    
    public TeamWinner(Team team) {
        Validate.notNull(team, "team can not be null");
        this.team = team;
    }
    
    @Override
    public String getMessage() {
        return this.getName() + Winner.DEFAULT_COLOR + " wygral/a!";
    }
    
    @Override
    public String getName() {
        return this.getTeam().getDisplayName();
    }
    
    public Team getTeam() {
        return this.team;
    }
}
