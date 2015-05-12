/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.region;

import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.map.BlockLocation;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class RegionFilter {
    public boolean canBuild(Material material) {
        return false;
    }
    
    public boolean isInside(BlockLocation location) {
        Validate.notNull(location, "location can not be null");
        return this.canBuild(location.getBlock().getMaterial());
    }
}
