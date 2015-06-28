/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.hologram;

import org.bukkit.entity.ArmorStand;
import pl.shg.arcade.api.location.Location;
import pl.shg.arcade.bukkit.BukkitLocation;

/**
 *
 * @author Aleksander
 */
public class Holograms {
    public static Hologram create(Location location) {
        return create(BukkitLocation.valueOf(location));
    }
    
    public static Hologram create(org.bukkit.Location location) {
        return new Hologram(BukkitLocation.getWorld().spawn(location, ArmorStand.class));
    }
}
