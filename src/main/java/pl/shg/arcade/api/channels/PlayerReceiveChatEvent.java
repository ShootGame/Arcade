/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.channels;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.command.Sender;
import pl.shg.arcade.api.event.CancelableEvent;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.text.ChatMessage;

/**
 *
 * @author Aleksander
 */
public class PlayerReceiveChatEvent extends CancelableEvent {
    private ChatMessage message;
    private Player player;
    private Sender sender;
    
    public PlayerReceiveChatEvent(Player player, Sender sender, ChatMessage message) {
        super(PlayerReceiveChatEvent.class);
        this.setMessage(message);
        this.setPlayer(player);
        this.setSender(sender);
    }
    
    public ChatMessage getMessage() {
        return this.message;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Sender getSender() {
        return this.sender;
    }
    
    public final void setMessage(ChatMessage message) {
        Validate.notNull(message, "message can not be null");
        this.message = message;
    }
    
    private void setPlayer(Player player) {
        Validate.notNull(player, "player can not be null");
        this.player = player;
    }
    
    private void setSender(Sender sender) {
        Validate.notNull(sender, "sender can not be null");
        this.sender = sender;
    }
}
