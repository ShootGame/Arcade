/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.map;

/**
 *
 * @author Aleksander
 */
public class NotLoadedMap extends Map {
    public NotLoadedMap(String name) {
        super(null, name, null, null);
    }
    
    @Override
    public boolean exists() {
        return false;
    }
}
