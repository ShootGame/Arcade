/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.human;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.event.CancelableEvent;
import pl.shg.arcade.api.location.Location;
import pl.shg.arcade.api.location.Spawn;

/**
 *
 * @author Aleksander
 */
public class PlayerRespawnMatchEvent extends CancelableEvent {
    private final Player player;
    private Spawn spawn;
    
    public PlayerRespawnMatchEvent(Player player, Spawn spawn) {
        super(PlayerRespawnMatchEvent.class);
        this.player = player;
        this.spawn = spawn;
    }
    
    public Location getLocation() {
        return this.getPlayer().getLocation();
    }
    
    public Spawn getSpawn() {
        return this.spawn;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public void setSpawn(Spawn spawn) {
        Validate.notNull(spawn, "spawn can not be null");
        this.spawn = spawn;
    }
}
