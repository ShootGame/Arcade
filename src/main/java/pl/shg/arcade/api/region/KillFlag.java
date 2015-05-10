/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.region;

import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.team.Team;

/**
 *
 * @author Aleksander
 */
public class KillFlag extends Flag {
    private Team owner;
    
    public KillFlag() {
        super("kill");
    }
    
    public KillFlag(String message) {
        super("kill", message);
    }
    
    public KillFlag(Team owner) {
        super("kill");
        this.setOwner(owner);
    }
    
    public KillFlag(Team owner, String message) {
        super("kill", message);
        this.setOwner(owner);
    }
    
    @Override
    public boolean canMove(Player player) {
        if (this.hasOwner() && !this.getOwner().equals(player.getTeam()) && !player.isDead()) {
            player.setHealth(0.0);
        } else if (!this.hasOwner() && !player.isDead()) {
            player.setHealth(0.0);
        }
        return true;
    }
    
    public Team getOwner() {
        return this.owner;
    }
    
    public boolean hasOwner() {
        return this.owner != null;
    }
    
    private void setOwner(Team owner) {
        this.owner = owner;
    }
}
