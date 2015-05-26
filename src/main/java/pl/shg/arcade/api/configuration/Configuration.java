/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.configuration;

import java.io.File;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Configuration {
    public static final String FILE = "map.yml";
    private final ConfigurationTechnology configuration;
    private final File file;
    private final Map map;
    
    public Configuration(Map map) {
        Validate.notNull(map, "map can not be null");
        String directory = Arcade.getMaps().getMapsDirectory().getPath();
        this.configuration = Arcade.getMaps().getConfiguration();
        this.file = new File(directory + File.separator + map.getName() + File.separator + Configuration.FILE);
        this.map = map;
    }
    
    public ConfigurationTechnology getConfiguration() {
        return this.configuration;
    }
    
    public File getFile() {
        return this.file;
    }
    
    public Map getMap() {
        return this.map;
    }
}
