/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.text;

/**
 *
 * @author Aleksander
 */
public class Message {
    private String source;
    
    public Message() {
        this(null);
    }
    
    public Message(String source) {
        this.source = source;
    }
    
    public String getSource() {
        return this.source;
    }
    
    public boolean hasSource() {
        return this.source != null;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
}
