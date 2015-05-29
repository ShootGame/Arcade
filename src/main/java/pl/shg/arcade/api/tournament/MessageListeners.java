/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.tournament;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.channels.ChatChannel;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.event.PlayerJoinTeamEvent;
import pl.shg.arcade.api.event.PlayerSetClassEvent;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.team.ObserverTeamBuilder;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public class MessageListeners {
    public void register() {
        Event.registerListener(
                new ClassChange(),
                new TeamChange()
        );
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
    private class ClassChange implements EventListener {
        @Override
        public Class<? extends Event> getEvent() {
            return PlayerSetClassEvent.class;
        }
        
        @Override
        public void handle(Event event) {
            PlayerSetClassEvent e = (PlayerSetClassEvent) event;
            if (e.isCancel()) {
                return;
            }
            
            String player = e.getPlayer().getDisplayName();
            String clazz = e.getNewClass().getName();
            MessageListeners.team(e.getPlayer(),
                    player + Color.GRAY + " zmienil/a klase na " + Color.GOLD + Color.BOLD + clazz);
        }
    }
    
    /**
     * Handling when player is switching between teams
     */
    private class TeamChange implements EventListener {
        @Override
        public Class<? extends Event> getEvent() {
            return PlayerJoinTeamEvent.class;
        }
        
        @Override
        public void handle(Event event) {
            PlayerJoinTeamEvent e = (PlayerJoinTeamEvent) event;
            if (e.isCancel()) {
                return;
            }
            
            String player = e.getPlayer().getDisplayName();
            String team = e.getTeam().getDisplayName();
            MessageListeners.info(player + Color.GRAY + " dolaczyl/a do " + team);
        }
    }
}
