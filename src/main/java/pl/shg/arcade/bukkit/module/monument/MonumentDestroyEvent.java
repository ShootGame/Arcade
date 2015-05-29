/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.monument;

import pl.shg.arcade.api.event.CancelableEvent;
import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public class MonumentDestroyEvent extends CancelableEvent {
    private boolean objectiveDestroyed;
    private Monument monument;
    private Player player;
    
    public MonumentDestroyEvent(Monument monument, Player player) {
        super(MonumentDestroyEvent.class);
        this.setObjectiveDestroyed(monument.getObjective().isDestroyed());
        this.setMonument(monument);
        this.setPlayer(player);
    }
    
    public Monument getMonument() {
        return this.monument;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public boolean isObjectiveDestroyed() {
        return this.objectiveDestroyed;
    }
    
    private void setObjectiveDestroyed(boolean objectiveDestroyed) {
        this.objectiveDestroyed = objectiveDestroyed;
    }
    
    private void setMonument(Monument monument) {
        this.monument = monument;
    }
    
    private void setPlayer(Player player) {
        this.player = player;
    }
}
