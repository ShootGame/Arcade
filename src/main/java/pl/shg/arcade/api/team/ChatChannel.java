/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.team;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.chat.ActionMessageType;
import pl.shg.arcade.api.chat.ChatMessage;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.PlayerChatEvent;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public abstract class ChatChannel {
    private final boolean spy;
    
    public ChatChannel(boolean spy) {
        this.spy = spy;
    }
    
    public abstract String getFormat(String[] args);
    
    private void handle(Sender sender, String author, String message) {
        ChatMessage chat = new ChatMessage();
        chat.setSender(sender);
        chat.setText(message);
        
        PlayerChatEvent event = new PlayerChatEvent(this, chat, sender);
        Event.callEvent(event);
        
        String format = this.getFormat(new String[] {author, event.getMessage().getText()});
        if (!event.isCancel()) {
            this.sendServerMessage(sender, format);
            if (this.isSpy()) {
                this.handleSpy(sender, format);
            }
        }
    }
    
    private void handleSpy(Sender sender, String message) {
        message = Color.LIGHT_PURPLE + Color.ITALIC + "[Spy] " + Color.RESET + message;
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            if (player.isSpying() && this.testSpy(sender, player)) {
                player.sendMessage(message);
            }
        }
    }
    
    public boolean isSpy() {
        return this.spy;
    }
    
    public abstract void sendActionMessage(ActionMessageType type, String message);
    
    public void sendMessage(Player player, String message) {
        Validate.notNull(player, "player can not be null");
        Validate.notNull(message, "message can not be null");
        this.handle(player, player.getChatName(), message);
    }
    
    public void sendMessage(Sender sender, String message) {
        Validate.notNull(sender, "sender can not be null");
        Validate.notNull(message, "message can not be null");
        this.handle(sender, sender.getName(), message);
    }
    
    public abstract void sendServerMessage(Sender sender, String message);
    
    public boolean testSpy(Sender sender, Player reciver) {
        return true;
    }
}
