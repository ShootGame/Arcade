/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.classes;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.event.CancelableEvent;
import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public final class PlayerSetClassEvent extends CancelableEvent {
    private ArcadeClass clazz;
    private Player player;
    
    public PlayerSetClassEvent(ArcadeClass clazz, Player player) {
        super(PlayerSetClassEvent.class);
        this.setNewClass(clazz);
        this.setPlayer(player);
    }
    
    public ArcadeClass getNewClass() {
        return this.clazz;
    }
    
    public ArcadeClass getOldClass() {
        return this.getPlayer().getArcadeClass();
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public void setNewClass(ArcadeClass clazz) {
        Validate.notNull(clazz, "clazz can not be null");
        this.clazz = clazz;
    }
    
    private void setPlayer(Player player) {
        Validate.notNull(player, "player can not be null");
        this.player = player;
    }
}
