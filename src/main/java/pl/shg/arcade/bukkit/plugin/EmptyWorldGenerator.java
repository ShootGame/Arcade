/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.plugin;

import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.location.Spawn;
import pl.shg.arcade.api.team.Team;

/**
 *
 * @author Aleksander
 */
public class EmptyWorldGenerator extends ChunkGenerator {
    private static Spawn worldDefaultSpawn;
    
    @Override
    public byte[] generate(World world, Random random, int x, int z) {
        return new byte[16 * 16 * 256]; // generate an empty world without blocks
    }
    
    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        Team observers = Arcade.getTeams().getObservers();
        if (observers != null && observers.getSpawns() != null) {
            int randomIndex = random.nextInt(observers.getSpawns().size());
            return convert(observers.getSpawns().get(randomIndex));
        } else {
            return convert(getWorldDefaultSpawn());
        }
    }
    
    public static Spawn getWorldDefaultSpawn() {
        if (worldDefaultSpawn == null) {
            worldDefaultSpawn = new Spawn(0.5, 64, 0.5, 0, 0, null);
        }
        
        return worldDefaultSpawn;
    }
    
    public static void setWorldDefaultSpawn(Spawn spawn) {
        worldDefaultSpawn = spawn;
    }
    
    private static Location convert(Spawn spawn) {
        return new Location(getWorld(), spawn.getX(), spawn.getY(), spawn.getZ());
    }
    
    private static World getWorld() {
        return Bukkit.getWorld("world");
    }
}
