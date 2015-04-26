/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander JagieĹ‚Ĺ‚o <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.PlayerManagement;
import pl.shg.arcade.api.map.team.kit.KitType;
import pl.shg.arcade.api.server.Server;
import pl.shg.arcade.api.match.Match;
import pl.shg.arcade.api.match.MatchManager;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.match.MatchType;
import pl.shg.arcade.api.match.PlayerWinner;
import pl.shg.arcade.api.match.TeamWinner;
import pl.shg.arcade.api.match.Winner;
import pl.shg.arcade.api.module.Module;

/**
 *
 * @author Aleksander
 */
public class ArcadeMatchManager implements MatchManager {
    private Match match;
    private MatchStatus status;
    
    @Override
    public Match getMatch() {
        return this.match;
    }
    
    @Override
    public MatchStatus getStatus() {
        if (this.status != null) {
            return this.status;
        } else {
            return MatchStatus.NOTHING;
        }
    }
    
    @Override
    public void setStatus(MatchStatus status) {
        this.status = status;
    }
    
    @Override
    public Winner getWinner() {
        switch (this.getType()) {
            case PLAYERS: return this.getWinnerPlayer();
            case TEAMS: return this.getWinnerTeam();
            default: return null;
        }
    }
    
    @Override
    public void startNew() {
        this.match = new Match(this.getType());
        this.setStatus(MatchStatus.PLAYING);
        this.broadcastStart();
        
        PlayerManagement players = Arcade.getPlayerManagement();
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            if (player.isObserver()) {
                players.setGhost(player, true);
            } else {
                players.setAsPlayer(player, KitType.BEGIN, false, true);
            }
        }
        players.refreshHiderForAll();
        
        // Modules
        for (Module module : Arcade.getModules().getActiveModules()) {
            module.enable();
        }
    }
    
    private void broadcastStart() {
        String label = Color.DARK_PURPLE + " # # # # # # # # # # # # # # # # ";
        
        Server server = Arcade.getServer();
        server.broadcast(label);
        server.broadcast(Color.DARK_PURPLE + " # # " + Color.GOLD + "   Mecz sie rozpoczal!  " + Color.RESET + Color.DARK_PURPLE + " # #");
        server.broadcast(label);
    }
    
    private PlayerWinner getWinnerPlayer() {
        
        return null;
    }
    
    private TeamWinner getWinnerTeam() {
        
        return null;
    }
    
    private MatchType getType() {
        if (Arcade.getTeams().getTeams().size() == 1) {
            return MatchType.PLAYERS;
        } else {
            return MatchType.TEAMS;
        }
    }
}
