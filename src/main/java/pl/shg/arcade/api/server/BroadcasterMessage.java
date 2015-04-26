/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.chat.ChatMessage;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class BroadcasterMessage extends ChatMessage {
    public static final BroadcasterSender SENDER = Arcade.getServers().getCurrentServer().getBroadcaster().getSender();
    
    public BroadcasterMessage(String message) {
        this.setSender(SENDER);
        this.setText(message);
    }
    
    @Override
    public boolean hasText() {
        return true;
    }
    
    @Override
    public final void setText(String text) {
        Validate.notNull(text, "text can not be null");
        super.setText(text);
    }
}
