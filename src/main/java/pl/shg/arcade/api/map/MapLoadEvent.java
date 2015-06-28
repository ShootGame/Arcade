/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.map;

import pl.shg.arcade.api.event.CancelableEvent;

/**
 *
 * @author Aleksander
 */
public class MapLoadEvent extends CancelableEvent {
    private final Map map;
    
    public MapLoadEvent(Map map) {
        super(MapLoadEvent.class);
        this.map = map;
    }
    
    public Map getMap() {
        return this.map;
    }
}
