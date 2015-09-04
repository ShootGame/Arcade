/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.abilities;

/**
 *
 * @author Aleksander
 */
public class AbilityKey {
    private final KeyType type;
    
    public AbilityKey(KeyType type) {
        this.type = type;
    }
    
    public KeyType getType() {
        return this.type;
    }
}
