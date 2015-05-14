/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander JagieĹ‚Ĺ‚o <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.cy;

import java.util.List;
import java.util.logging.Level;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.ArcadeClass;
import pl.shg.arcade.api.map.MapVersion;
import pl.shg.arcade.api.map.Protocol;
import pl.shg.arcade.api.util.Validate;
import pl.shg.arcade.bukkit.ScoreboardManager;

/**
 *
 * @author Aleksander
 */
public class CyLoader extends CyConfiguration {
    private final FileConfiguration file;
    private final Map map;
    private final Protocol protocol;
    
    public CyLoader(FileConfiguration file, Map map, Protocol protocol) {
        Validate.notNull(file, "file can not be null");
        Validate.notNull(map, "map can not be null");
        Validate.notNull(protocol, "protocol can not be null");
        this.file = file;
        this.map = map;
        this.protocol = protocol;
    }
    
    public void load(boolean test) throws ConfigurationException {
        FileConfiguration f = this.getFile();
        Map m = this.getMap();
        m.setMapProto(this.getProtocol()); // Map protocol
        
        // authors - authors of the map
        String author = f.getString("author");
        List<String> authors = f.getStringList("authors");
        if (author != null) {
            m.setAuthors(new String[] {author});
        } else if (authors == null || authors.isEmpty()) {
            this.throwError(CError.NOT_SET, "author/authors", null);
        } else {
            m.setAuthors(authors.toArray(new String[authors.size()])); // setup
        }
        
        // version - version of the map
        String version = f.getString("version");
        if (version == null) {
            this.throwError(CError.NOT_SET, "version", null);
        } else if (!MapVersion.isValid(version)) {
            this.throwError(CError.UNKNOWN_VALUE, "version", null);
        } else {
            m.setVersion(MapVersion.valueOf(version)); // setup
        }
        
        // game - game on this map
        String objective = f.getString("objective");
        if (objective == null) {
            this.throwError(CError.NOT_SET, "objective", null);
        } else {
            m.setObjective(objective); // setup
        }
        
        // If the map loads only to test - stop loading
        if (test) {
            return;
        }
        
        // kits - kits on this map
        ConfigurationSection kits = f.getConfigurationSection("kits");
        if (kits != null) {
            CyKitsLoader kitsLoader = new CyKitsLoader(f);
            Arcade.getTeams().setKits(kitsLoader.getKits());
        } else {
            Log.log(Level.INFO, "Nie znaleziono zadnych zestawow dla mapy " + m.getName());
        }
        
        // teams - teams on this map
        int teams = f.getConfigurationSection("teams").getKeys(false).size();
        if (teams < 2) {
            this.throwError(CError.NOT_SET, "game", new Object[] {"$OBS", "oraz minimalnie jedna druzyna"});
        } else {
            CyTeamsLoader teamsLoader = new CyTeamsLoader(f);
            Arcade.getTeams().setTeams(teamsLoader.getTeams()); // setup
            ScoreboardManager.setTeams(); // Scoreboards
        }
        
        // classes - classes on this map
        ConfigurationSection classes = f.getConfigurationSection("classes");
        if (classes != null) {
            CyClassesLoader classesLoader = new CyClassesLoader(f);
            m.setSwitchingClassAllowed(classesLoader.isSwitchingAllowed());
            for (ArcadeClass clazz : classesLoader.getClasses()) {
                m.registerClass(clazz);
            }
        } else {
            Log.log(Level.INFO, "Nie znaleziono zadnych klas dla mapy " + m.getName());
        }
        
        // regions - regions on this map
        ConfigurationSection regions = f.getConfigurationSection("regions");
        if (regions != null) {
            CyRegionsLoader regionsLoader = new CyRegionsLoader(f);
            m.getRegions().setRegions(regionsLoader.getRegions());
        } else {
            Log.log(Level.INFO, "Nie znaleziono zadnych regionow dla mapy " + m.getName());
        }
    }
    
    public FileConfiguration getFile() {
        return this.file;
    }
    
    public Map getMap() {
        return this.map;
    }
    
    public Protocol getProtocol() {
        return this.protocol;
    }
}
