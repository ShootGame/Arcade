/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.perform;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Sheep;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.bukkit.BukkitLocation;
import pl.shg.arcade.bukkit.Config;

/**
 *
 * @author Aleksander
 */
public class CTSPerform extends Perform {
    private Location location;
    
    public CTSPerform(FileConfiguration config, String id, int seconds, boolean repeating, int times) {
        super("cts", config, id, seconds, repeating, times);
    }
    
    @Override
    public void run() {
        Sheep sheep = BukkitLocation.getWorld().spawn(this.getLocation(), Sheep.class);
        sheep.setAdult();
        sheep.setColor(DyeColor.WHITE);
        sheep.setMaxHealth(9999.0D);
        sheep.setHealth(9999.0D);
        sheep.setCanPickupItems(false);
        
        Event.callEvent(new CTSSpawnEvent(sheep));
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public void setLocation(Location location) {
        this.location = location;
    }
    
    public static CTSPerform create(Module module, FileConfiguration config,
            String id, int seconds, boolean repeating, int times) {
        CTSPerform perform = new CTSPerform(config, id, seconds, repeating, times);
        double x = Config.getValueDouble(config, module, id + ".perform." + perform.getName() + ".x", 0);
        int y = Config.getValueInt(config, module, id + ".perform." + perform.getName() + ".y", 16);
        double z = Config.getValueDouble(config, module, id + ".perform." + perform.getName() + ".z", 0);
        
        perform.setLocation(new Location(BukkitLocation.getWorld(), x, y, z));
        return perform;
    }
    
    public static class CTSSpawnEvent extends Event {
        private Sheep sheep;
        
        public CTSSpawnEvent(Sheep sheep) {
            super(CTSSpawnEvent.class);
            this.setSheep(sheep);
        }
        
        public Sheep getSheep() {
            return this.sheep;
        }
        
        private void setSheep(Sheep sheep) {
            this.sheep = sheep;
        }
    }
}
