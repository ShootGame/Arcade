/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.event;

import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.team.Team;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public final class PlayerJoinTeamEvent extends CancelableEvent {
    private Player player;
    private Team team;
    
    public PlayerJoinTeamEvent(Player player, Team team) {
        super(PlayerJoinTeamEvent.class);
        this.setPlayer(player);
        this.setTeam(team);
    }
    
    public Team getOldTeam() {
        return this.getPlayer().getTeam();
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Team getTeam() {
        return this.team;
    }
    
    public void setTeam(Team team) {
        Validate.notNull(team, "team can not be null");
        this.team = team;
    }
    
    private void setPlayer(Player player) {
        Validate.notNull(player, "player can not be null");
        this.player = player;
    }
}
