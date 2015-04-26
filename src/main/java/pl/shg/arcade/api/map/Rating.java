/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.map;

import java.util.UUID;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Rating {
    private final UUID id, player;
    private final Map map;
    private final int rate;
    private final String server;
    private final long time;
    
    public Rating(UUID id, Map map, UUID player, int rate, String server, long time) {
        Validate.notNull(id, "id can not be null");
        Validate.notNull(map, "map can not be null");
        Validate.notNull(player, "player can not be null");
        Validate.isTrue(rate < 0 || rate > 5, "rate can be only between 1 and 5");
        Validate.notNull(server, "server can not be null");
        Validate.notNegative(time, "time can not be negative");
        
        this.id = id;
        this.map = map;
        this.player = player;
        this.rate = rate;
        this.server = server;
        this.time = time;
    }
    
    public UUID getID() {
        return this.id;
    }
    
    public Map getMap() {
        return this.map;
    }
    
    public Player getPlayer() {
        return Arcade.getServer().getPlayer(this.getPlayersID());
    }
    
    public UUID getPlayersID() {
        return this.player;
    }
    
    public int getRate() {
        return this.rate;
    }
    
    public String getServer() {
        return this.server;
    }
    
    public long getTime() {
        return this.time;
    }
}
