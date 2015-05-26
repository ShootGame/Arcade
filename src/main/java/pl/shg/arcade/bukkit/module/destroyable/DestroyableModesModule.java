/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import java.io.File;
import java.util.Date;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.util.Version;

/**
 *
 * @author Aleksander
 */
public class DestroyableModesModule extends Module {
    public DestroyableModesModule() {
        super(new Date(2015, 5, 11), "destroyable-modes", Version.valueOf("1.0"));
    }
    
    @Override
    public void disable() {
        
    }
    
    @Override
    public void enable() {
        
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        
    }
    
    @Override
    public void unload() {
        
    }
}
