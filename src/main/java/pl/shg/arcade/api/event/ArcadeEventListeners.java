/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.event;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Sound;
import pl.shg.arcade.api.channels.GlobalChannel;
import pl.shg.arcade.api.channels.PlayerChatEvent;
import pl.shg.arcade.api.channels.TeamsChannel;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.text.ChatMessage;
import pl.shg.arcade.api.text.Color;
import pl.shg.commons.users.ChatStatus;

/**
 *
 * @author Aleksander
 */
public class ArcadeEventListeners implements EventListener {
    @EventSubscribtion(event = PlayerChatEvent.class)
    public void handleClientChat(Event event) {
        PlayerChatEvent e = (PlayerChatEvent) event;
        
        if (e.getSender() instanceof Player) {
            Player player = (Player) e.getSender();
            if (player.getCommons().getChatStatus() != ChatStatus.ENABLED) {
                e.setCancel(true);
                
                player.sendError("Nie udalo sie wyslac Twojej wiadomosci poniewaz wylaczyles/as chat!");
                player.sendError("Aby go wlaczyc wejdz w ustawienia chatu w opcjach gry.");
            }
        }
    }
    
    @EventSubscribtion(event = PlayerChatEvent.class)
    public void handleGlobalKeyListener(Event event) {
        PlayerChatEvent e = (PlayerChatEvent) event;
        
        if (e.getChannel() instanceof GlobalChannel) {
            return;
        }
        
        String source = e.getMessage().getSource();
        if (source.length() > 1 && source.startsWith("!")) {
            // we need to cancel this event because it is executed already in the team's channel
            e.setCancel(true);
            Arcade.getTeams().getGlobalChannel().sendMessage(e.getSender(), source.substring(1));
        }
    }
    
        @EventSubscribtion(event = PlayerChatEvent.class)
        public void handleMention(Event event) {
        PlayerChatEvent e = (PlayerChatEvent) event;
        
        StringBuilder builder = new StringBuilder();
        for (String word : e.getMessage().getSource().split(" ")) {
            if (word.startsWith("@")) {
                Player player = Arcade.getServer().getPlayer(word.substring(1));
                if (player != null) {
                    builder.append(player.getChatName()).append(Color.GRAY).append(" ");
                    if (e.getChannel() instanceof TeamsChannel &&
                            !((TeamsChannel) e.getChannel()).getTeam().equals(player.getTeam())) {
                        // don't notify players that can's see this message
                        continue;
                    }
                    
                    Arcade.getPlayerManagement().playSound(player, Sound.MENTION);
                    player.sendMessage(Color.YELLOW + e.getSender().getName() + " oznaczyl/a Ciebie w wiadomosci na chacie.");
                    continue;
                }
            }
            builder.append(word).append(" ");
        }
        
        String message = builder.toString();
        
        ChatMessage chat = new ChatMessage();
        chat.setSender(e.getSender());
        chat.setSource(message.substring(0, message.length() - 1));
        e.setMessage(chat);
    }
}
