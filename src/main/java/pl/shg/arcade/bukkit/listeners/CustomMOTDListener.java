/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.api.text.Icons;

/**
 *
 * @author Aleksander
 */
public class CustomMOTDListener implements Listener {
    @EventHandler
    public void onServerListPing(ServerListPingEvent e) {
        String motd = null;
        MatchStatus status = Arcade.getMatches().getStatus();
        
        if (status == MatchStatus.STARTING) {
            motd = this.build(Color.GREEN);
        } else if (status == MatchStatus.PLAYING) {
            motd = this.build(Color.GOLD);
        } else if (status == MatchStatus.ENDING) {
            motd = this.build(Color.DARK_RED);
        } else {
            motd = this.build(Color.GRAY);
        }
        
        e.setMotd(motd);
    }
    
    private String build(String color) {
        String left = Icons.LEFT_POINTING_DOUBLE.toString();
        String right = Icons.RIGHT_POINTING_DOUBLE.toString();
        String map = Arcade.getMaps().getCurrentMap().getDisplayName();
        
        return '\t' + color + right + " " + Color.AQUA + Color.BOLD + map + Color.RESET + color + " " + left;
    }
}
