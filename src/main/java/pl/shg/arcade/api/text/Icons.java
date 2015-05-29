/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.text;

import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public enum Icons {
    NO('\u2716', Color.DARK_RED), // ✖
    YES('\u2714', Color.GREEN), // ✔
    ;
    
    private final char icon;
    private final String color;
    
    private Icons(char icon) {
        this(icon, null);
    }
    
    private Icons(char icon, String color) {
        Validate.notNull(icon, "icon can not be null");
        this.icon = icon;
        this.color = color;
    }
    
    public String getColoredIcon() {
        if (this.hasColor()) {
            return this.getColor() + this.toString() + Color.RESET;
        } else {
            return this.toString();
        }
    }
    
    public char getIcon() {
        return this.icon;
    }
    
    public String getColor() {
        return this.color;
    }
    
    public boolean hasColor() {
        return this.color != null;
    }
    
    @Override
    public String toString() {
        return Character.toString(this.getIcon());
    }
}
