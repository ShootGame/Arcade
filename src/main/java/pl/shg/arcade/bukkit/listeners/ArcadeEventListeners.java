/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.listeners;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Sound;
import pl.shg.arcade.api.chat.ChatMessage;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.event.PlayerChatEvent;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.team.TeamChat;
import pl.shg.commons.util.ChatStatus;

/**
 *
 * @author Aleksander
 */
public class ArcadeEventListeners {
    public void registerListeners() {
        Event.registerListener(new ClientChatListener());
        Event.registerListener(new MentionListener());
    }
    
    private class ClientChatListener implements EventListener {
        @Override
        public Class<? extends Event> getEvent() {
            return PlayerChatEvent.class;
        }
        
        @Override
        public void handle(Event event) {
            PlayerChatEvent e = (PlayerChatEvent) event;
            
            if (e.getSender() instanceof Player) {
                Player player = (Player) e.getSender();
                if (player.getClientSettings().getChat() != ChatStatus.ENABLED) {
                    e.setCancel(true);
                    player.sendError("Nie udalo sie wyslac Twojej wiadomosci poniewaz wylaczyles/as chat!");
                    player.sendError("Aby go wlaczyc wejdz w ustawienia chatu w opcjach gry.");
                }
            }
        }
    }
    
    private class MentionListener implements EventListener {
        @Override
        public Class<? extends Event> getEvent() {
            return PlayerChatEvent.class;
        }
        
        @Override
        public void handle(Event event) {
            PlayerChatEvent e = (PlayerChatEvent) event;
            
            StringBuilder builder = new StringBuilder();
            for (String word : e.getMessage().getText().split(" ")) {
                if (word.startsWith("@")) {
                    Player player = Arcade.getServer().getPlayer(word.substring(1));
                    if (player != null) {
                        builder.append(player.getChatName()).append(Color.GRAY).append(" ");
                        if (e.getChannel() instanceof TeamChat && !((TeamChat) e.getChannel()).getTeam().equals(player.getTeam())) {
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
            chat.setText(message.substring(0, message.length() - 1));
            e.setMessage(chat);
        }
    }
}
