/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.region;

import java.util.ArrayList;
import java.util.List;
import pl.shg.arcade.api.map.BlockLocation;
import pl.shg.arcade.api.map.Location;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public final class Region {
    private final List<RegionFilter> filters;
    private final List<Flag> flags;
    private BlockLocation max;
    private BlockLocation min;
    private Team owner;
    private final String path;
    
    public Region(BlockLocation min, BlockLocation max, String path) {
        Validate.notNull(path, "path can not be null");
        this.filters = new ArrayList<>();
        this.flags = new ArrayList<>();
        this.setMax(max);
        this.setMin(min);
        this.path = path;
    }
    
    public void addFilter(RegionFilter filter) {
        Validate.notNull(filter, "filter can not be null");
        this.filters.add(filter);
    }
    
    public RegionFilter getFilter(Class<? extends RegionFilter> filter) {
        Validate.notNull(filter, "filter can not be null");
        for (RegionFilter target : this.getFilters()) {
            if (target.getClass().equals(filter)) {
                return target;
            }
        }
        return null;
    }
    
    public List<RegionFilter> getFilters() {
        return this.filters;
    }
    
    public boolean hasFilter(Class<? extends RegionFilter> filter) {
        Validate.notNull(filter, "filter can not be null");
        return this.getFilter(filter) != null;
    }
    
    public boolean addFlag(Flag flag) {
        Validate.notNull(flag, "flag can not be null");
        for (Flag f : this.getFlags()) {
            if (f.getID().equals(flag.getID())) {
                return false;
            }
        }
        this.flags.add(flag);
        return true;
    }
    
    public List<Flag> getFlags() {
        return this.flags;
    }
    
    public BlockLocation getMax() {
        return this.max;
    }
    
    public void setMax(BlockLocation max) {
        Validate.notNull(max, "max can not be null");
        this.max = max;
    }
    
    public BlockLocation getMin() {
        return this.min;
    }
    
    public void setMin(BlockLocation min) {
        Validate.notNull(min, "min can not be null");
        this.min = min;
    }
    
    public Team getOwner() {
        return this.owner;
    }
    
    public void setOwner(Team owner) {
        this.owner = owner;
    }
    
    public String getPath() {
        return this.path;
    }
    
    public boolean isIn(Location location) {
        Validate.notNull(location, "location can not be null");
        return
                this.max.getBlockX() >= (int) location.getX() &&
                this.max.getBlockY() >= (int) location.getY() &&
                this.max.getBlockZ() >= (int) location.getZ() &&
                
                this.min.getBlockX() <= (int) location.getX() &&
                this.min.getBlockY() <= (int) location.getY() &&
                this.min.getBlockZ() <= (int) location.getZ();
    }
}
