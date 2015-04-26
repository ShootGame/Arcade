/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.module;

import java.io.File;
import pl.shg.arcade.api.map.ConfigurationException;

/**
 *
 * @author Aleksander
 */
public interface IModule {
    /**
     * Called when a match ends
     */
    void disable();
    
    /**
     * Called when a match begins
     */
    void enable();
    
    /**
     * Called when a map has been loaded
     * This method is for load all your module-data from the configuration file
     * @param file The configuration file of this map
     * @throws ConfigurationException when you have error in your configuration file
     */
    void load(File file) throws ConfigurationException;
    
    /**
     * Called when a map has been unloaded
     * This method is for unregister all your listeners
     */
    void unload();
}
