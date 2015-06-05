/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.rotation;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.map.Map;

/**
 *
 * @author Aleksander
 */
public class Rotation {
    private final HashMap<UUID, Map> maps;
    
    public Rotation() {
        this.maps = new HashMap<>();
    }
    
    public void addMap(Map map) {
        Validate.notNull(map, "map can not be null");
        this.maps.put(UUID.randomUUID(), map);
    }
    
    public Collection<Map> getMaps() {
        return this.maps.values();
    }
}
