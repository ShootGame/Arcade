/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.server;

import java.util.List;
import java.util.logging.Level;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Broadcaster {
    private int index = 0;
    private List<String> messages;
    private final BroadcasterSender sender = new BroadcasterSender();
    private final BroadcasterSettings settings;
    
    public Broadcaster(BroadcasterSettings settings) {
        Validate.notNull(settings, "settings can not be null");
        this.settings = settings;
    }
    
    public synchronized void broadcast() {
        String format = this.getSettings().getFormat();
        String message = this.getMessages().get(0);
        try {
            message = this.getMessages().get(this.index);
        } catch (IndexOutOfBoundsException ex) {
            this.index = 0;
        }
        this.index++;
        Sender console = Arcade.getCommands().getConsoleSender();
        
        Log.log(Level.INFO, "[Broadcaster] " + message);
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            player.sendChatMessage(console, new BroadcasterMessage(this.translate(
                    Color.translate(format), Color.translate(message), player)));
        }
    }
    
    public List<String> getMessages() {
        return this.messages;
    }
    
    public void setMessages(List<String> messages) {
        Validate.notNull(messages, "messages can not be null");
        this.messages = messages;
    }
    
    public BroadcasterSender getSender() {
        return this.sender;
    }
    
    public BroadcasterSettings getSettings() {
        return this.settings;
    }
    
    private String translate(String format, String message, Player player) {
        Validate.notNull(format, "format can not be null");
        Validate.notNull(message, "message can not be null");
        Validate.notNull(player, "player can not be null");
        message = message
                .replace("$playerName", player.getName())
                .replace("$playerChatName", player.getChatName())
                .replace("$playerDisplayName", player.getDisplayName())
                .replace("$playerUUID", player.getUUID().toString())
                .replace("$teamID", player.getTeam().getID())
                .replace("$teamName", player.getTeam().getName())
                .replace("$teamDisplayName", player.getTeam().getDisplayName());
        
        return format.replace("$message", message);
    }
}
