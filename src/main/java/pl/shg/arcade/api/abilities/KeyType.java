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
public enum KeyType {
    ALPHA(1),
    BETA(2),
    GAMMA(3),
    DELTA(4),
    ;
    
    private final int mcKey;
    
    private KeyType(int mcKey) {
        this.mcKey = mcKey;
    }
    
    public int getMinecraftKey() {
        return this.mcKey;
    }
}
