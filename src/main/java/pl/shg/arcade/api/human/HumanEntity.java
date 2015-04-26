/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.human;

import pl.shg.arcade.api.map.Location;
import pl.shg.arcade.api.map.Spawn;

/**
 *
 * @author Aleksander
 */
public interface HumanEntity extends Damageable, Feedable, Titleable {
    Location getLocation();
    
    void teleport(Location location);
    
    void teleport(Spawn spawn);
}
