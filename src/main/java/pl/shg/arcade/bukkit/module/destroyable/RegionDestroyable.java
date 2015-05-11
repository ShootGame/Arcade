/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.human.Player;
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
    public boolean canDestroy(Player player) {
        return false;
    }
    
    @Override
    public void destroy(Player player) {
        if (this.canDestroy(player)) {
            DestroyableDestroyEvent destroy = new DestroyableDestroyEvent(this);
            Event.callEvent(destroy);
            
            if (!destroy.isCancel()) {
                DestroyableDestroyedEvent destroyed = new DestroyableDestroyedEvent(this);
                Event.callEvent(destroyed);
            }
        }
    }
    
    @Override
    public int getPercent() {
        return 100;
    }
    
    @Override
    public DestroyStatus getStatus() {
        return null;
    }
    
    public Region getRegion() {
        return this.region;
    }
}
