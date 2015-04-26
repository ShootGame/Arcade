/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.map;

/**
 *
 * @author Aleksander
 */
public interface ConfigurationTechnology {
    void load(Configuration configuration, boolean test) throws ConfigurationException;
    
    String name();
    
    void registerModules() throws ConfigurationException;
    
    void unload();
}
