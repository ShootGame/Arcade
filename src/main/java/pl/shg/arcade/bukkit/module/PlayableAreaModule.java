/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.module;

import java.io.File;
import java.util.Date;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class PlayableAreaModule extends Module implements BListener {
    private String message;
    private int xMax, yMax = 256, zMax, xMin, yMin = 0, zMin;
    
    public PlayableAreaModule() {
        super(new Date(2014, 12, 6), "playable-area", "1.0");
        this.getDocs().setDescription("Ten moduł umożliwia ustawienie granicy " +
                "rozgrywki na mapie. Ustawiając region, w którym znajduje się " +
                "dana rozgryka blokuje się jednocześnie nisczenie oraz stawianie " +
                "bloków po za tym regionem. Istnieje także opcja całkowitego " +
                "wyłączenia budowania, dzięki czemu jest to świetny moduł na " +
                "proste mapy Team-death match.");
        this.deploy(true);
    }
    
    @Override
    public void disable() {}
    
    @Override
    public void enable() {}
    
    @Override
    public void load(File file) throws ConfigurationException {
        FileConfiguration config = Config.get(file);
        if (!Config.isSet(config, this)) {
            return;
        }
        if (!Config.hasOptions(config, this)) {
            return;
        }
        Listeners.register(this);
        
        for (String option : Config.getOptions(config, this)) {
            switch (option.toLowerCase()) {
                case "message":
                    this.message = Config.getValueMessage(config, this,
                            "Nie mozesz budowac ani niszczyc poza granica mapy.", true);
                    break;
                case "x-max":
                    this.xMax = Config.getValueInt(config, this, option);
                    break;
                case "y-max":
                    this.yMax = Config.getValueInt(config, this, option);
                    break;
                case "z-max":
                    this.zMax = Config.getValueInt(config, this, option);
                    break;
                case "x-min":
                    this.xMin = Config.getValueInt(config, this, option);
                    break;
                case "y-min":
                    this.yMin = Config.getValueInt(config, this, option);
                    break;
                case "z-min":
                    this.zMin = Config.getValueInt(config, this, option);
                    break;
            }
        }
        
        if (this.xMax == 0 && this.xMin == 0 && this.zMax == 0 && this.zMin == 0) {
            // TODO register an events that listen to the PlayerGetKitEvent or something similar
        }
    }
    
    @Override
    public void unload() {
        Listeners.unregister(this);
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (this.isOutside(e.getBlock().getLocation())) {
            e.setCancelled(true);
            if (this.message != null) {
                e.getPlayer().sendMessage(Color.RED + Color.translate(this.message));
            }
        }
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (this.isOutside(e.getBlock().getLocation())) {
            e.setCancelled(true);
            if (this.message != null) {
                e.getPlayer().sendMessage(Color.RED + Color.translate(this.message));
            }
        }
    }
    
    private boolean isOutside(Location location) {
        if (location.getBlockX() > this.xMax || location.getBlockX() < this.xMin) {
            return true;
        } else if (location.getBlockY() > this.yMax || location.getBlockY() < this.yMin) {
            return true;
        } else if (location.getBlockZ() > this.zMax || location.getBlockZ() < this.zMin) {
            return true;
        } else {
            return false;
        }
    }
}
