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

/**
 *
 * @author Aleksander
 */
public class ArcadeEventListeners {
    public void registerListeners() {
        Event.registerListener(new PlayerChat());
    }
    
    private class PlayerChat implements EventListener {
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
