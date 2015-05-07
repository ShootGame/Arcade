/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.chat;

import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public enum ActionMessageType {
    ERROR(new Color('4')),
    INFO(new Color('F')),
    SUCCESS(new Color('2'));
    
    private final Color color;
    
    private ActionMessageType(Color color) {
        Validate.notNull(color, "color can not be null");
        this.color = color;
    }
    
    public Color getColor() {
        return this.color;
    }
}
