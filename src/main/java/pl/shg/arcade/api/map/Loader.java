/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.map;

import java.util.List;

/**
 *
 * @author Aleksander
 */
public interface Loader {
    static final int MAX_NAME_LENGTH = 16;
    
    List<Map> getMaps();
    
    void loadMapList();
}
