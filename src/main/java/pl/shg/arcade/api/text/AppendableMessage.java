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
public class AppendableMessage extends Message {
    public AppendableMessage() {
        super();
    }
    
    public AppendableMessage(String source) {
        super(source);
    }
    
    public AppendableMessage append(String text) {
        if (text != null) {
            this.setSource(this.getSource() + text);
        }
        return this;
    }
}
