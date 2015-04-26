/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.map;

import java.io.File;
import java.util.List;

/**
 *
 * @author Aleksander
 */
public interface MapManager {
    ConfigurationTechnology getConfiguration();
    
    Map getCurrentMap();
    
    void updateCurrentMap();
    
    Map getMap(String name);
    
    Map getMapExact(String name);
    
    File getMapsDirectory();
    
    Map getNextMap();
    
    void setNextMap(Map next);
    
    List<Map> getMaps();
    
    void setMaps(List<Map> maps);
    
    WorldManager getWorlds();
    
    void setWorlds(WorldManager worlds);
}
