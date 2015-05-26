/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.debug.impl;

import pl.shg.arcade.api.configuration.Configuration;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.configuration.ConfigurationTechnology;

/**
 *
 * @author Aleksander
 */
public class DebugConfiguration implements ConfigurationTechnology {
    @Override
    public void load(Configuration configuration, boolean test) throws ConfigurationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public String name() {
        return "debug";
    }
    
    @Override
    public void registerModules() throws ConfigurationException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void unload() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
