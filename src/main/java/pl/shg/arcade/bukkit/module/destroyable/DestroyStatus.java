/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import pl.shg.arcade.api.chat.Color;

/**
 *
 * @author Aleksander
 */
public enum DestroyStatus {
    UNTOUCHED(Color.GREEN),
    TOUCHED(Color.YELLOW),
    TOUCHED_SILENT(Color.GREEN),
    DESTROYED(Color.DARK_RED),
    ;
    
    private final String color;
    
    private DestroyStatus(String color) {
        this.color = color;
    }
    
    public String getColor() {
        return this.color;
    }
}
