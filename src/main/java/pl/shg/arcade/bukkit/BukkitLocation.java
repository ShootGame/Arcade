/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import pl.shg.arcade.api.Arcade;

/**
 *
 * @author Aleksander
 */
public class BukkitLocation {
    public static World getWorld() {
        return Bukkit.getWorld(Arcade.getMaps().getCurrentMap().getName());
    }
    
    public static Location valueOf(pl.shg.arcade.api.location.Location location) {
        return new Location(getWorld(), location.getX(), location.getY(), location.getZ());
    }
    
    public static Location valueOf(double x, double y, double z) {
        return new Location(getWorld(), x, y, z);
    }
}
