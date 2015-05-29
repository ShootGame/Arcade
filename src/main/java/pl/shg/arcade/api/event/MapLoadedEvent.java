/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.event;

import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.map.Map;

/**
 *
 * @author Aleksander
 */
public class MapLoadedEvent extends Event {
    private Map map;
    
    public MapLoadedEvent(Map map) {
        super(MapLoadedEvent.class);
        this.setMap(map);
    }
    
    public Map getMap() {
        return this.map;
    }
    
    private void setMap(Map map) {
        Validate.notNull(map, "map can not be null");
        this.map = map;
    }
}
