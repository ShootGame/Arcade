/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.team;

import java.util.List;
import pl.shg.arcade.api.location.Spawn;
import pl.shg.arcade.api.permissions.ArcadeTeam;

/**
 *
 * @author Aleksander
 */
public interface TeamBuilder {
    String getID();
    
    String getName();
    
    ArcadeTeam getPermissions();
    
    List<Spawn> getSpawns();
    
    boolean isFrendlyFire();
}
