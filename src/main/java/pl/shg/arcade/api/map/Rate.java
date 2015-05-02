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
import pl.shg.shootgame.api.server.Servers;

/**
 *
 * @author Aleksander
 */
public class Rate extends Rating {
    private final Player player;
    
    public Rate(Player player, int rate) {
        super(
                UUID.randomUUID(),
                Arcade.getMaps().getCurrentMap(),
                player.getUUID(),
                rate,
                Servers.getOnline().getID(),
                System.currentTimeMillis()
        );
        this.player = player;
    }
    
    @Override
    public Player getPlayer() {
        return this.player;
    }
    
    public void register() {
        
    }
}
