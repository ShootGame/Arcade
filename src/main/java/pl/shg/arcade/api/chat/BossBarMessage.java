/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.chat;

import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class BossBarMessage {
    private String text;
    
    public BossBarMessage() {}
    
    public BossBarMessage(String text) {
        this.setText(text);
    }
    
    public void appendText(String text) {
        Validate.notNull(text, "text can not be null");
        String current = "";
        if (this.getText() != null) {
            current = this.getText();
        }
        this.setText(current + text);
    }
    
    public String getText() {
        return this.text;
    }
    
    public final void setText(String text) {
        Validate.notNull(text, "text can not be null");
        this.text = text;
    }
}
