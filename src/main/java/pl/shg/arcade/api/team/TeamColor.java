/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.team;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.text.Color;

/**
 *
 * @author Aleksander
 */
public enum TeamColor {
    AQUA(Color.AQUA, new int[] {85, 255, 255}, Color.Wool.CYAN),
    BLACK(Color.BLACK, new int[] {0, 0, 0}, Color.Wool.BLACK),
    BLUE(Color.BLUE, new int[] {85, 85, 255}, Color.Wool.LIGHT_BLUE),
    DARK_AQUA(Color.DARK_AQUA, new int[] {0, 170, 170}, Color.Wool.CYAN),
    DARK_BLUE(Color.DARK_BLUE, new int[] {0, 0, 170}, Color.Wool.BLUE),
    DARK_GRAY(Color.DARK_GRAY, new int[] {85, 85, 85}, Color.Wool.GRAY),
    DARK_GREEN(Color.DARK_GREEN, new int[] {0, 170, 0}, Color.Wool.GREEN),
    DARK_PURPLE(Color.DARK_PURPLE, new int[] {170, 0, 170}, Color.Wool.PURPLE),
    DARK_RED(Color.DARK_RED, new int[] {170, 0, 0}, Color.Wool.RED),
    GOLD(Color.GOLD, new int[] {255, 170, 0}, Color.Wool.ORANGE),
    GRAY(Color.GRAY, new int[] {170, 170, 170}, Color.Wool.LIGHT_GRAY),
    GREEN(Color.GREEN, new int[] {85, 255, 85}, Color.Wool.LIME),
    LIGHT_PURPLE(Color.LIGHT_PURPLE, new int[] {255, 85, 255}, Color.Wool.PINK),
    RED(Color.RED, new int[] {255, 85, 85}, Color.Wool.RED),
    WHITE(Color.WHITE, new int[] {255, 255, 85}, Color.Wool.WHITE),
    YELLOW(Color.YELLOW, new int[] {255, 255, 255}, Color.Wool.YELLOW),
    ;
    
    private final String color;
    private final int[] rgb;
    private final Color.Wool wool;
    
    private TeamColor(String color, int[] rgb, Color.Wool wool) {
        Validate.notNull(color, "color can not be null");
        Validate.notNull(rgb, "rgb can not be null");
        Validate.isTrue(rgb.length == 3, "rgb can not be invalid");
        Validate.notNull(wool, "wool can not be null");
        
        this.color = color;
        this.rgb = rgb;
        this.wool = wool;
    }
    
    public String getColor() {
        return this.color;
    }
    
    public int[] getRGB() {
        return this.rgb;
    }
    
    public Color.Wool getWool() {
        return this.wool;
    }
}
