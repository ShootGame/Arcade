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
public class ChatMessage {
    private Sender sender;
    private String text;
    
    public Sender getSender() {
        return this.sender;
    }
    
    public String getText() {
        return this.text;
    }
    
    public boolean hasSender() {
        return this.sender != null;
    }
    
    public boolean hasText() {
        return this.text != null;
    }
    
    public boolean isOffensive() {
        return false; // TOOD use ShooGame's global ChatModerator API to perform this boolean
    }
    
    public void setSender(Sender sender) {
        this.sender = sender;
    }
    
    public void setText(String text) {
        this.text = text;
    }
}
