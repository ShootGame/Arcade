/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.loader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.configuration.Configuration;
import pl.shg.arcade.api.configuration.ConfigurationTechnology;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.MapManager;
import pl.shg.arcade.api.util.TextFileReader;

/**
 *
 * @author Aleksander
 */
@Deprecated // Why GitHub? I'll write this shit in the file
public class URLMapLoader implements Loader {
    private final List<Map> maps;
    private final String url;
    
    public URLMapLoader(String url) {
        Validate.notNull(url, "url can not be null");
        this.maps = new ArrayList<>();
        this.url = url;
    }
    
    @Override
    public List<Map> getMaps() {
        return this.maps;
    }
    
    @Override
    public void loadMapList() {
        List<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new URL(this.url).openStream());
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().replace(" ", "_");
                if (line.length() > Loader.MAX_NAME_LENGTH) {
                    continue;
                }
                
                File mapFile = new File(Arcade.getMaps().getMapsDirectory().getPath() + File.separator + line + File.separator + Configuration.FILE);
                if (mapFile.exists()) {
                    lines.add(line);
                } else {
                    Log.log(Level.WARNING, "Brak konfiguracji mapy " + line);
                }
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(URLMapLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(URLMapLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        MapManager mapManager = Arcade.getMaps();
        for (TextFileReader.Line line : new TextFileReader(lines).getLines()) {
            Map map = new Map(null, line.getValue(), null, null);
            
            ConfigurationTechnology loader = mapManager.getConfiguration();
            loader.load(new Configuration(map), true);
            this.maps.add(map);
        }
    }
}
