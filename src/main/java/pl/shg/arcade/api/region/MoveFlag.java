/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.region;

import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.team.Team;

/**
 *
 * @author Aleksander
 */
public class MoveFlag extends Flag {
    private Team owner;
    
    public MoveFlag() {
        super("move");
        this.setOwner(null);
    }
    
    public MoveFlag(Team owner) {
        super("move");
        this.setOwner(owner);
    }
    
    public MoveFlag(Team owner, String message) {
        super("move", message);
        this.setOwner(owner);
    }
    
    @Override
    public boolean canMove(Player player) {
        return player.isTeam(this.getOwner());
    }
    
    public Team getOwner() {
        return this.owner;
    }
    
    private void setOwner(Team owner) {
        this.owner = owner;
    }
}
