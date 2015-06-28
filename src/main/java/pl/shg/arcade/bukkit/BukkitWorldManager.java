/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.Validate;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.map.Map;
import pl.shg.arcade.bukkit.plugin.EmptyWorldGenerator;
import pl.shg.arcade.api.location.WorldManager;

/**
 *
 * @author Aleksander
 */
public class BukkitWorldManager implements WorldManager {
    private final Server server;
    
    public BukkitWorldManager(Server server) {
        Validate.notNull(server);
        this.server = server;
    }
    
    @Override
    public void load(Map map) throws IOException {
        Validate.notNull(map);
        
        File from = new File(this.server.getWorldContainer(), map.getName());
        File to = new File(map.getWorldName());
        
        if (to.exists()) {
            to.delete();
        }
        
        FileUtils.copyDirectory(from, to);
        new File(to, "uid.dat").delete();
        new File(to, "session.lock").delete();
        
        WorldCreator creator = new WorldCreator(map.getWorldName());
        creator.environment(World.Environment.NORMAL);
        creator.generateStructures(false);
        creator.generator(new EmptyWorldGenerator());
        creator.type(WorldType.FLAT);
        
        World world = this.server.createWorld(creator);
        world.setAutoSave(false);
        world.setDifficulty(this.server.getWorlds().get(0).getDifficulty());
        world.setPVP(true);
    }
    
    @Override
    public void unloadCurrent() {
        this.unload(null);
    }
    
    @Override
    public void unload(Map map) {
        Map current = Arcade.getMaps().getCurrentMap();
        if (map == null && current != null) {
            map = current;
        }
        
        if (map != null) {
            this.server.unloadWorld(map.getWorldName(), true);
        }
    }
}
