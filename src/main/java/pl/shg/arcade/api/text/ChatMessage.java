/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.text;

import pl.shg.arcade.api.command.Sender;

/**
 *
 * @author Aleksander
 */
public class ChatMessage extends Message {
    private Sender sender;
    
    public ChatMessage() {
        super();
    }
    
    public Sender getSender() {
        return this.sender;
    }
    
    public boolean hasSender() {
        return this.sender != null;
    }
    
    public boolean isOffensive() {
        return false; // TOOD use ShooGame's global ChatModerator API to perform this boolean
    }
    
    public void setSender(Sender sender) {
        this.sender = sender;
    }
}
