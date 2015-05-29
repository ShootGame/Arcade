/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.region;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.location.Direction;
import pl.shg.arcade.api.team.Team;

/**
 *
 * @author Aleksander
 */
public class TeleportFlag extends Flag {
    private Direction direction;
    private Team owner;
    
    public TeleportFlag(Direction direction) {
        super("teleport");
        this.direction = direction;
    }
    
    public TeleportFlag(Direction direction, Team owner) {
        super("teleport");
        this.direction = direction;
        this.owner = owner;
    }
    
    public TeleportFlag(Direction direction, Team owner, String message) {
        super("teleport", message);
        this.direction = direction;
        this.owner = owner;
    }
    
    @Override
    public boolean canMove(Player player) {
        handleMove(player);
        return true;
    }
    
    public Direction getDirection() {
        return this.direction;
    }
    
    public Team getOwner() {
        return this.owner;
    }
    
    public void setDirection(Direction direction) {
        Validate.notNull(direction, "direction can not be null");
        this.direction = direction;
    }
    
    public void setOwner(Team owner) {
        this.owner = owner;
    }
    
    private void handleMove(Player player) {
        if (!(this.owner != null && !player.getTeam().equals(this.owner))) {
            player.teleport(this.direction);
        }
    }
}
