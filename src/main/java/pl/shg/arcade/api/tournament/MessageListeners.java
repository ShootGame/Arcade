/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.tournament;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.channels.ChatChannel;
import pl.shg.arcade.api.classes.PlayerSetClassEvent;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.event.EventSubscribtion;
import pl.shg.arcade.api.event.Priority;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.team.ObserverTeamBuilder;
import pl.shg.arcade.api.team.PlayerJoinTeamEvent;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class MessageListeners implements EventListener {
    public void register() {
        Event.registerListener(this);
    }
    
    public static void info(String message) {
        Arcade.getServer().broadcast(getPrefix("INFO") + message);
    }
    
    public static void team(Player player, String message) {
        if (player.getTeam().getID().equals(ObserverTeamBuilder.getTeamID())) {
            return;
        }
        
        ChatChannel channel = player.getTeam().getChat();
        if (channel != null) {
            channel.sendServerMessage(player, getPrefix("TEAM") + message);
        }
    }
    
    private static String getPrefix(String source) {
        return Color.WHITE + "[" + Color.AQUA + source + Color.WHITE + "] " + Color.RESET;
    }
    
    /**
     * Handling when player is switching between classes
     */
    @EventSubscribtion(event = PlayerSetClassEvent.class, priority = Priority.LOWEST)
    public void handleSwitchingClasses(PlayerSetClassEvent e) {
        if (e.isCancel()) {
            return;
        }
        
        String player = e.getPlayer().getDisplayName();
        String clazz = e.getNewClass().getName();
        MessageListeners.team(e.getPlayer(),
                player + Color.GRAY + " zmienil/a klase na " + Color.GOLD + Color.BOLD + clazz);
    }
    
    /**
     * Handling when player is switching between teams
     */
    @EventSubscribtion(event = PlayerJoinTeamEvent.class, priority = Priority.LOWEST)
    public void handleSwitchingTeams(PlayerJoinTeamEvent e) {
        if (e.isCancel()) {
            return;
        }
        
        String player = e.getPlayer().getDisplayName();
        String team = e.getTeam().getDisplayName();
        MessageListeners.info(player + Color.GRAY + " dolaczyl/a do " + team);
    }
}
