/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.match;

import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class PlayerWinner implements Winner {
    private final Player player;
    
    public PlayerWinner(Player player) {
        Validate.notNull(player, "player can not be null");
        this.player = player;
    }
    
    @Override
    public String getMessage() {
        return this.getName() + Winner.DEFAULT_COLOR + " wygral/a!";
    }
    
    @Override
    public String getName() {
        return this.getPlayer().getDisplayName();
    }
    
    public Player getPlayer() {
        return this.player;
    }
}
