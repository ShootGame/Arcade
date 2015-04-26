/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Rotation {
    private final List<Map> maps;
    private final String name;
    private final ArcadeServer server;
    
    public Rotation(String name, ArcadeServer server) {
        Validate.notNull(name, "name can not be null");
        Validate.notNull(server, "server can not be null");
        this.maps = new ArrayList<>();
        this.name = name;
        this.server = server;
    }
    
    public void addMap(Map map) {
        Validate.notNull(map, "map can not be null");
        this.maps.add(map);
    }
    
    public List<Map> getMaps() {
        return this.maps;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ArcadeServer getServer() {
        return this.server;
    }
}
