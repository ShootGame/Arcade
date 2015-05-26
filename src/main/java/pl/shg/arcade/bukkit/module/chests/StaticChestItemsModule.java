/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.chests;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class StaticChestItemsModule extends Module implements BListener {
    private final List<StaticChest> chests = new ArrayList<>();
    
    public StaticChestItemsModule() {
        super(new Date(2015, 04, 19), "static-chest-items", Version.valueOf("1.0"));
        this.getDocs().setDescription("Ten moduł umożliwia ustawienie statycznych " +
                "przedmiotów w skrzynkach. Przedmioty po wyjęciu ze skrzynki nie " +
                "są usuwane. Jest to jedno z najważniejszych zabezpieczeń przeciwko " +
                "graczom niszczących skrzynki w drużynach.");
    }
    
    @Override
    public void disable() {}
    
    @Override
    public void enable() {}
    
    @Override
    public void load(File file) throws ConfigurationException {
        FileConfiguration config = Config.get(file);
        World world = Bukkit.getWorld(Arcade.getMaps().getCurrentMap().getName());
        
        for (String location : Config.getValueList(config, this, ".chests")) {
            String[] coords = location.split(":");
            try {
                int x = Integer.parseInt(coords[0]);
                int y = Integer.parseInt(coords[1]);
                int z = Integer.parseInt(coords[2]);
                
                StaticChest chest = new StaticChest();
                Location locObject = new Location(world, x, y, z);
                
                Material type = locObject.getBlock().getType();
                if (type == Material.CHEST) {
                    Chest c = (Chest) locObject.getBlock().getState();
                    
                    chest.setContents(c.getBlockInventory().getContents());
                    chest.setLocation(locObject);
                    
                    this.chests.add(chest);
                }
            } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                Logger.getLogger(StaticChestItemsModule.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if (!this.chests.isEmpty()) {
            Listeners.register(this);
        }
    }
    
    @Override
    public void unload() {
        Listeners.register(this);
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null || e.getPlayer().isSneaking()) {
            return;
        }
        
        Material type = e.getClickedBlock().getType();
        if (type == Material.CHEST) {
            StaticChest chest = this.getChest(e.getClickedBlock().getLocation());
            if (chest == null) {
                return;
            }
            
            Chest c = (Chest) e.getClickedBlock().getState();
            c.getBlockInventory().setContents(chest.getContents());
        }
    }
    
    private StaticChest getChest(Location location) {
        for (StaticChest chest : this.chests) {
            Location chestLoc = chest.getLocation();
            if (chestLoc.getBlockX() == location.getBlockX() &&
                    chestLoc.getBlockY() == location.getBlockY() &&
                    chestLoc.getBlockZ() == location.getBlockZ()) {
                return chest;
            }
        }
        return null;
    }
    
    private class StaticChest {
        private ItemStack[] contents;
        private Location location;
        
        public ItemStack[] getContents() {
            return this.contents;
        }
        
        public Location getLocation() {
            return this.location;
        }
        
        public void setContents(ItemStack[] contents) {
            this.contents = contents;
        }
        
        public void setLocation(Location location) {
            this.location = location;
        }
    }
}
