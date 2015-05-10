/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.scheduler;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Sound;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.command.def.CancelCommand;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.server.Server;
import pl.shg.arcade.api.team.ObserverTeamBuilder;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class BeginScheduler implements Runnable {
    private static int defaultSeconds = 20;
    private boolean endIgnored = false;
    private static int id;
    
    private int seconds;
    
    public BeginScheduler(int seconds) {
        if (seconds < 0) {
            this.seconds = getDefaultSeconds();
        } else {
            this.seconds = seconds;
        }
    }
    
    @Override
    public void run() {
        if (CancelCommand.isDisabled() || !this.checkEnd()) {
            Arcade.getServer().getScheduler().cancel(getID());
            return;
        }
        this.print();
        
        switch (this.seconds) {
            case 0:
                for (Player player : Arcade.getServer().getConnectedPlayers()) {
                    player.sendTitle("");
                    player.sendSubtitle(Color.DARK_PURPLE + "Mecz sie rozpoczal!");
                    Arcade.getPlayerManagement().playSound(player, Sound.BEGINS);
                }
                break;
            case 1: case 2: case 3:
                for (Player player : Arcade.getServer().getConnectedPlayers()) {
                    if (!player.isObserver()) {
                        player.sendTitle(Color.GREEN + this.seconds);
                    }
                    Arcade.getPlayerManagement().playSound(player, Sound.BEGINING);
                }
                break;
        }
        
        if (this.seconds <= 0) {
            Arcade.getServer().getScheduler().cancel();
            Arcade.getMatches().startNew();
        } else {
            this.seconds--;
        }
    }
    
    // will return false if is force-end
    private boolean checkEnd() {
        if (this.endIgnored) {
            return true;
        } else if (Arcade.getMatches().getStatus() != MatchStatus.STARTING) {
            return false;
        }
        
        for (Team team : Arcade.getTeams().getTeams()) {
            if (!team.getID().equals(ObserverTeamBuilder.getTeamID())) {
                if (team.getPlayers().size() < team.getMinimum()) {
                    Arcade.getServer().broadcast(Color.AQUA + "Odliczanie startu meczu zostalo " +
                            "zatrzymane z powodu braku wystarczajacej ilosci graczy w druzynach.");
                    return false;
                }
            }
        }
        return true;
    }
    
    private void print() {
        Server server = Arcade.getServer();
        for (int second : server.getScheduler().seconds()) {
            if (this.seconds == second) {
                String msg = Color.GREEN + "Mecz wystaruje za " + Color.DARK_RED + "{0}" + Color.GREEN + " sekund";
                server.broadcast(msg.replace("{0}", String.valueOf(second)));
            }
        }
    }
    
    public static int getDefaultSeconds() {
        return defaultSeconds;
    }
    
    public static int getID() {
        return id;
    }
    
    public static void setDefaultSeconds(int seconds) {
        Validate.isTrue(seconds <= 3, "seconds are incorrent");
        defaultSeconds = seconds;
    }
    
    public static void setID(int id) {
        BeginScheduler.id = id;
    }
}
