/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.BlockLocation;
import pl.shg.arcade.api.region.Region;

/**
 *
 * @author Aleksander
 */
public class RegionDestroyable implements Destroyable {
    private final Region region;
    
    public RegionDestroyable(Region region) {
        this.region = region;
    }
    
    @Override
    public boolean canDestroy(Player player, BlockLocation block) {
        return false;
    }
    
    @Override
    public int getPercent() {
        return 100;
    }
    
    @Override
    public DestroyStatus getStatus() {
        return DestroyStatus.UNTOUCHED;
    }
    
    public Region getRegion() {
        return this.region;
    }
}
