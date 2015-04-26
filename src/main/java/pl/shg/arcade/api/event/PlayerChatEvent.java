/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.event;

import pl.shg.arcade.api.chat.ChatMessage;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.map.team.ChatChannel;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public final class PlayerChatEvent extends CancelableEvent {
    private ChatChannel channel;
    private ChatMessage message;
    private Sender sender;
    
    public PlayerChatEvent(ChatChannel channel, ChatMessage message, Sender sender) {
        super(PlayerChatEvent.class);
        this.setChannel(channel);
        this.setMessage(message);
        this.setSender(sender);
    }
    
    public ChatChannel getChannel() {
        return this.channel;
    }
    
    public ChatMessage getMessage() {
        return this.message;
    }
    
    public Sender getSender() {
        return this.sender;
    }
    
    public void setMessage(ChatMessage message) {
        Validate.notNull(message, "message can not be null");
        this.message = message;
    }
    
    private void setChannel(ChatChannel channel) {
        Validate.notNull(channel, "channel can not be null");
        this.channel = channel;
    }
    
    private void setSender(Sender sender) {
        Validate.notNull(sender, "sender can not be null");
        this.sender = sender;
    }
}
