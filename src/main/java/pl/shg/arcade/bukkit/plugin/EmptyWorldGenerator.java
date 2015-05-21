/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.plugin;

import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.map.Spawn;

/**
 *
 * @author Aleksander
 */
public class EmptyWorldGenerator extends ChunkGenerator {
    @Override
    public byte[] generate(World world, Random random, int x, int z) {
        return new byte[16 * 16 * 256];
    }
    
    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        List<Spawn> spawns = Arcade.getTeams().getObservers().getSpawns();
        if (spawns != null) {
            Spawn spawn = spawns.get(random.nextInt(spawns.size()));
            return new Location(world, spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch());
        } else {
            return new Location(world, 0, 64, 0);
        }
    }
}
