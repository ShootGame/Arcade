/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.kit;

import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.PlayerManagement;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public abstract class Option {
    public static final PlayerManagement PLAYERS = Arcade.getPlayerManagement();
    private final String name;
    private String value;
    
    public Option(String name) {
        this(name, null);
    }
    
    public Option(String name, String value) {
        Validate.notNull(name, "name can not be null");
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean hasValue() {
        return value != null;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public abstract void apply(Player player);
    
    public static String[] fromString(String string) {
        Validate.notNull(string, "string can not be null");
        String[] split = string.split("-");
        String name = split[0];
        if (split.length > 1) {
            return new String[] {name, split[1]};
        } else {
            return new String[] {name};
        }
    }
}
