/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.loader;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.location.WorldManager;

/**
 *
 * @author Aleksander
 */
public class MapLoader {
    private static WorldManager world;
    
    public static WorldManager getWorld() {
        return world;
    }
    
    public static void setWorld(WorldManager world) {
        Validate.notNull(world, "world can not be null");
        MapLoader.world = world;
    }
}
