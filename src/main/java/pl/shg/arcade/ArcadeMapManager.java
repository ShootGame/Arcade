/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.configuration.ConfigurationTechnology;
import pl.shg.arcade.api.location.WorldManager;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.api.map.MapComparator;
import pl.shg.arcade.api.map.MapManager;
import pl.shg.arcade.api.map.NotLoadedMap;
import pl.shg.arcade.api.rotation.Rotation;
import pl.shg.arcade.api.server.MiniGameServer;
import pl.shg.arcade.api.util.CrashHandler;

/**
 *
 * @author Aleksander
 */
public class ArcadeMapManager implements MapManager {
    private final ConfigurationTechnology configuration;
    private Map current;
    private final File directory;
    private Map next;
    private List<Map> maps;
    private final Random random = new Random();
    private WorldManager worlds;
    
    public ArcadeMapManager(ConfigurationTechnology configuration, File directory) {
        Validate.notNull(configuration, "configuration can not be null");
        Validate.notNull(directory, "directory can not be null");
        this.configuration = configuration;
        this.directory = directory;
    }
    
    @Override
    public ConfigurationTechnology getConfiguration() {
        return this.configuration;
    }
    
    @Override
    public Map getCurrentMap() {
        return this.current;
    }
    
    @Override
    public void updateCurrentMap() {
        // check if currently running...
        this.current = this.getNextMap();
        Rotation rotation = MiniGameServer.ONLINE.getRotation();
        
        boolean nextMap = false;
        for (int i = 0; i < rotation.getMaps().size(); i++) {
            Map map = (Map) rotation.getMaps().toArray()[i];
            if (nextMap) {
                this.setNextMap(map);
                return;
            } else if (map.equals(this.current)) { // TODO uuid
                nextMap = true;
            }
        }
        
        if (nextMap) {
            this.setNextMap(null);
        } else if (this.current.equals(Arcade.getMaps().getCurrentMap())) {
            this.setNextMap((Map) rotation.getMaps().toArray()[this.random.nextInt(rotation.getMaps().size())]);
        } else if (this.getNextMap().getName().equals(this.current.getName())) {
            this.setNextMap(null);
            Log.log(Level.SEVERE, "Aby plugin Arcade dzialal prawidlowo wymagane sa minimum 2 rotacyjne mapy!");
        }
    }
    
    @Override
    public Map getMap(String name) {
        Validate.notNull(name, "name can not be null");
        name = name.toLowerCase().replace(" ", "_");
        for (Map map : this.getMaps()) {
            if (map.getName().toLowerCase().contains(name.toLowerCase())) {
                return map;
            }
        }
        return null;
    }
    
    @Override
    public Map getMapExact(String name) {
        Validate.notNull(name, "name can not be null");
        name = name.toLowerCase().replace(" ", "_");
        for (Map map : this.getMaps()) {
            if (map.getName().toLowerCase().equals(name)) {
                return map;
            }
        }
        return null;
    }
    
    @Override
    public File getMapsDirectory() {
        return this.directory;
    }
    
    @Override
    public Map getNextMap() {
        return next;
    }
    
    @Override
    public void setNextMap(Map next) {
        this.next = next;
    }
    
    @Override
    public List<Map> getMaps() {
        return this.maps;
    }
    
    @Override
    public void setMaps(List<Map> maps) {
        Validate.notNull(maps, "maps can not be null");
        Collections.sort(maps, new MapComparator());
        this.maps = maps;
    }
    
    @Override
    public WorldManager getWorlds() {
        return this.worlds;
    }
    
    @Override
    public void setWorlds(WorldManager worlds) {
        Validate.notNull(worlds, "worlds can not be null");
        this.worlds = worlds;
        
        try {
            Rotation rotation = MiniGameServer.ONLINE.getRotation();
            if (this.getNextMap() != null) {
                this.getWorlds().load(this.getNextMap());
            } else if (rotation.getMaps().isEmpty()) {
                Log.log(Level.SEVERE, "Brak map w rotacji serwera " + MiniGameServer.ONLINE.getShoot().getID());
            } else {
                for (Map map : rotation.getMaps()) {
                    if (!(map instanceof NotLoadedMap)) {
                        this.setNextMap(map);
                        Arcade.getServer().getScheduler().runCycle(0);
                        return;
                    }
                }
            }
        } catch (IOException ex) {
            new CrashHandler("cycling", ex).crash();
        }
    }
}
