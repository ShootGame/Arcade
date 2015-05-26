/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.team;

import java.util.List;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.location.Spawn;
import pl.shg.arcade.api.permissions.ArcadeTeam;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class ObserverTeamBuilder implements TeamBuilder {
    private List<Spawn> spawns;
    
    @Override
    public String getID() {
        return getTeamID();
    }
    
    @Override
    public String getName() {
        return "Obserwatorzy";
    }
    
    @Override
    public ArcadeTeam getPermissions() {
        return Arcade.getPermissions().getObservers();
    }
    
    @Override
    public List<Spawn> getSpawns() {
        return this.spawns;
    }
    
    public void setSpawns(List<Spawn> spawns) {
        Validate.notNull(spawns, "spawns can not be null");
        this.spawns = spawns;
    }
    
    @Override
    public boolean isFrendlyFire() {
        return true;
    }
    
    public static String getTeamID() {
        return "-OBS";
    }
}
