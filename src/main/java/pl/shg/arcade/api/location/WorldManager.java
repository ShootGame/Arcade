/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.location;

import java.io.File;
import java.io.IOException;
import pl.shg.arcade.api.map.Map;

/**
 *
 * @author Aleksander
 */
public interface WorldManager {
    public static final File DIRECTORY = new File("matches");
    
    void load(Map map) throws IOException ;
    
    void unloadCurrent();
    
    void unload(Map map);
}
