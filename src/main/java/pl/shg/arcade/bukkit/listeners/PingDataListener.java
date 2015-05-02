/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.map.team.Team;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.shootgame.api.util.ArcadeData;

/**
 *
 * @author Aleksander
 */
public class PingDataListener implements Listener {
    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {
        if (Arcade.getMatches().getStatus() == MatchStatus.NOTHING) {
            e.setMotd(ArcadeData.toData(new Object[] {null, 0, 0, 0}));
        }
        
        Object[] data = new Object[] {
            this.getMap(),
            this.getStatus(),
            this.getPlayers(),
            this.getSlots()
        };
        e.setMotd(ArcadeData.toData(data));
    }
    
    private String getMap() {
        return Arcade.getMaps().getCurrentMap().getDisplayName();
    }
    
    private int getPlayers() {
        return Bukkit.getOnlinePlayers().size();
    }
    
    private int getSlots() {
        int i = 0;
        for (Team team : Arcade.getTeams().getTeams()) {
            i += team.getSlots();
        }
        return i;
    }
    
    private int getStatus() {
        switch (Arcade.getMatches().getStatus()) {
            case NOTHING: return 0;
            case STARTING: return 1;
            case PLAYING: return 2;
            case ENDING: return 3;
            default: return 0; // called only when status is null
            
        }
    }
}
