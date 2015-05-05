/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.event;

import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.Location;
import pl.shg.arcade.api.map.Spawn;
import pl.shg.arcade.api.util.Validate;

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
