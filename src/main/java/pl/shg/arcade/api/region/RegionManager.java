/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.region;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.location.Location;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class RegionManager {
    private final Map map;
    private List<Region> regions;
    
    public RegionManager(Map map) {
        Validate.notNull(map, "map can not be null");
        this.map = map;
        this.regions = new ArrayList<>();
    }
    
    public Map getMap() {
        return this.map;
    }
    
    public List<Region> getRegions() {
        return this.regions;
    }
    
    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
    
    public List<Region> getRegions(Location location) {
        Validate.notNull(location, "location can not be null");
        List<Region> regionsList = new ArrayList<>();
        for (Region region : this.getRegions()) {
            if (region.isIn(location)) {
                regionsList.add(region);
            }
        }
        return regionsList;
    }
    
    public void registerRegion(Region region) {
        Validate.notNull(region, "region can not be null");
        this.regions.add(region);
        Log.log(Level.INFO, "Zarejestrowano region '" + region.getPath() + "'.");
    }
}
