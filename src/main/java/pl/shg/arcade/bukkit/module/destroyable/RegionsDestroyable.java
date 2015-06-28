/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import java.util.HashMap;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.location.BlockLocation;
import pl.shg.arcade.api.region.Region;
import pl.shg.arcade.api.team.Team;

/**
 *
 * @author Aleksander
 */
public class RegionsDestroyable extends DestroyableObject {
    private final String name;
    private final Region region;
    private final HashMap<Setting, Object> settings;
    
    public RegionsDestroyable(String name, Region region) {
        this.name = name;
        this.region = region;
        this.settings = new HashMap<>();
    }
    
    @Override
    public void appendSetting(Setting setting, Object value) {
        this.settings.put(setting, value);
    }
    
    @Override
    public boolean canDestroy(Player player, BlockLocation block) {
        return false;
    }
    
    @Override
    public void destroy(Player player) {
        
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public Team getOwner() {
        return null;
    }
    
    @Override
    public double getPercent() {
        return 0.100;
    }
    
    @Override
    public Object getSettingValue(Setting setting) {
        return this.settings.get(setting);
    }
    
    @Override
    public DestroyStatus getStatus() {
        return DestroyStatus.UNTOUCHED;
    }
    
    public Region getRegion() {
        return this.region;
    }
}
