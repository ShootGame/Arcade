/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class Options {
    public static final File FILE = new File("./arcade.properties");
    public static final String[] HEADER = new String[] {
        " Arcade plugin (version ${project.version}) main configuration file.",
        " The other part of configuration (rotations and broadcaster) is available here:",
        " https://raw.githubusercontent.com/ShootGame/Servers/master/ server name here... /settings.txt",
        "",
        " Copyright (C) 2014 TheMolkaPL - All Rights Reserved",
        " Unauthorized copying of this file, via any medium is strictly prohibited",
        " Proprietary and confidential",
        " Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014"
    };
    public static final String VERSION_REGEX = "${project.version}";
    
    private final Properties properties;
    private final HashMap<String, String> values;
    
    public Options() {
        Log.log(Level.INFO, "Ladowanie konfiguracji serwera z pliku " + Options.FILE);
        this.properties = new Properties();
        this.values = new HashMap<>();
        this.loadFile();
    }
    
    public Properties getProperties() {
        return this.properties;
    }
    
    public String getValue(String key) {
        return this.getValue(key, null);
    }
    
    public String getValue(String key, String def) {
        Validate.notNull(key, "key can not be null");
        if (this.values.containsKey(key)) {
            return this.values.get(key);
        } else {
            return def;
        }
    }
    
    public HashMap<String, String> getValues() {
        return this.values;
    }
    
    private void loadFile() {
        if (!Options.FILE.exists()) {
            try {
                Options.FILE.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try {
            this.properties.load(new FileInputStream(Options.FILE));
        } catch (IOException ex) {
            Logger.getLogger(Options.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Object key : this.properties.keySet()) {
            this.values.put(key.toString(), this.properties.getProperty(key.toString()));
        }
        
        if (this.values.isEmpty()) {
            Log.log(Level.SEVERE, "Nie znaleziono znadnych ustawien w pliku arcade.proprties!");
        }
    }
}
