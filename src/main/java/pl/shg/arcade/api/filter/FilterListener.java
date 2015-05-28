/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.filter;

import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.location.BlockLocation;
import pl.shg.arcade.api.location.Location;

/**
 *
 * @author Aleksander
 */
public class FilterListener {
    // These methods should be overrided by the super classes
    
    // test if can build at the specifited location and/or with special block
    // default: false
    public boolean canBuild(Location location, Material material) {
        return false;
    }
    
    // test if block is inside in the region
    // default: canBuild(Location, Material)
    public boolean isInside(BlockLocation location) {
        return this.canBuild(location, location.getBlock().getMaterial());
    }
}
