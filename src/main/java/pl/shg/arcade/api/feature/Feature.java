/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.feature;

import org.apache.commons.lang3.Validate;

/**
 *
 * @author Aleksander
 */
public class Feature {
    private final String id;
    private final Class location;
    
    public Feature(String id, Class location) {
        Validate.notNull(id);
        Validate.notNull(location);
        
        this.id = id;
        this.location = location;
    }
    
    public String getID() {
        return this.id;
    }
    
    public Class getLocation() {
        return this.location;
    }
}
