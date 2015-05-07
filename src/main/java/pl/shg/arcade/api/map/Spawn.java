/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.map;

import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Spawn extends Direction {
    private Team team;
    
    public Spawn(double x, int y, double z, float yaw, float pitch, Team team) {
        super(x, y, z, yaw, pitch);
        this.team = team;
    }
    
    public Team getTeam() {
        return this.team;
    }
    
    public void setTeam(Team team) {
        Validate.notNull(team, "team can not be null");
        this.team = team;
    }
}
