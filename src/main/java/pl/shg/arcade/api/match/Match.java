/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.match;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.PlayerManagement;
import pl.shg.arcade.api.Sound;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.server.Server;

/**
 *
 * @author Aleksander
 */
public class Match {
    private final Time time;
    private final MatchType type;
    
    public Match() {
        this.time = new Time();
        
        if (Arcade.getTeams().getTeams().size() == 1) {
            this.type = MatchType.PLAYERS;
        } else {
            this.type = MatchType.TEAMS;
        }
    }
    
    public void end() {
        this.end(null);
    }
    
    /**
     * @param winner Winner of this match, you can use the following
     * {@link PlayerWinner} - a player won the game,
     * {@link TeamWinner} - a team won the game,
     * {@link UnresolvedWinner} - teams/players have the same scores,
     * <code>null</code> - match fails
     */
    public void end(Winner winner) {
        if (Arcade.getMatches().getStatus() != MatchStatus.PLAYING) {
            throw new UnsupportedOperationException("Can not end the match when it is not running.");
        }
        
        Arcade.getMatches().setStatus(MatchStatus.ENDING);
        this.broadcastEnd(winner);
        this.playEndSound(winner);
        Arcade.getServer().getScheduler().runCycle(-1);
        
        PlayerManagement players = Arcade.getPlayerManagement();
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            players.setAsObserver(player, false, false, true);
        }
        players.refreshHiderForAll();
        
        // Modules
        for (Module module : Arcade.getModules().getActiveModules()) {
            module.disable();
        }
        
        // TOOD MySQL
    }
    
    public Time getTime() {
        return this.time;
    }
    
    public MatchType getType() {
        return this.type;
    }
    
    public boolean isRunning() {
        return this.getTime().isRunning();
    }
    
    private void broadcastEnd(Winner winner) {
        String label = Color.DARK_PURPLE + " # # # # # # # # # # # # # # # # ";
        
        Server server = Arcade.getServer();
        server.broadcast(label);
        server.broadcast(Color.DARK_PURPLE + " # # " + Color.GOLD + "       Koniec  gry!       " + Color.RESET + Color.DARK_PURPLE + " # #");
        if (winner != null) {
            server.broadcast(Color.DARK_PURPLE + " # # " + Color.GOLD + Color.ITALIC + winner.getMessage() + Color.RESET + Color.DARK_PURPLE + " # # ");
        }
        server.broadcast(label);
    }
    
    private void playEndSound(Winner winner) {
        PlayerManagement players = Arcade.getPlayerManagement();
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            if (winner != null) {
                player.sendSubtitle(winner.getMessage());
            }
            
            if (player.isObserver()) {
                player.sendTitle(Color.AQUA + "Koniec gry!");
                players.playSound(player, Sound.ENEMY_LOST);
            } else if (winner instanceof PlayerWinner) {
                if (player.getUUID().equals(((PlayerWinner) winner).getPlayer().getUUID())) {
                    player.sendTitle(Color.GREEN + "Zwyciestwo!");
                    players.playSound(player, Sound.ENEMY_LOST);
                } else {
                    player.sendTitle(Color.RED + "Porazka!");
                    players.playSound(player, Sound.ENEMY_WON);
                }
            } else if (winner instanceof TeamWinner) {
                if (player.getTeam().getID().equals(((TeamWinner) winner).getTeam().getName())) {
                    player.sendTitle(Color.GREEN + "Zwyciestwo!");
                    players.playSound(player, Sound.ENEMY_LOST);
                } else {
                    player.sendTitle(Color.RED + "Porazka!");
                    players.playSound(player, Sound.ENEMY_WON);
                }
            } else {
                player.sendTitle(Color.AQUA + "Koniec gry!");
                players.playSound(player, Sound.ENEMY_LOST);
            }
        }
    }
}
