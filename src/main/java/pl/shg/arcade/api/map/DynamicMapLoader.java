/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.map;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import pl.shg.arcade.api.Arcade;

/**
 *
 * @author Aleksander
 */
public class DynamicMapLoader implements Loader {
    private final List<Map> maps;
    
    public DynamicMapLoader() {
        this.maps = new ArrayList<>();
    }
    
    @Override
    public List<Map> getMaps() {
        return this.maps;
    }
    
    @Override
    public void loadMapList() {
        File mapsDir = Arcade.getMaps().getMapsDirectory();
        for (String directory : mapsDir.list()) {
            File file = new File(mapsDir + File.separator + directory);
            if (file.exists() && file.isDirectory()) {
                this.load(directory, new File(mapsDir + File.separator + directory + File.separator + Configuration.FILE));
            }
        }
    }
    
    private void load(String name, File file) {
        if (file.exists() && file.isFile()) {
            Map map = new Map(null, name, null);
            
            ConfigurationTechnology loader = Arcade.getMaps().getConfiguration();
            loader.load(new Configuration(map), true);
            this.maps.add(map);
        }
    }
}
