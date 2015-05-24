/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module;

import java.io.File;
import java.util.Date;
import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.ThunderChangeEvent;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class NoThunderModule extends Module implements BListener {
    public NoThunderModule() {
        super(new Date(2015, 3, 18), "no-thunder", Version.valueOf("1.0"));
        this.getDocs().setDescription("Ten moduł blokuje zmianę pogody na burze.");
        this.deploy(true);
    }
    
    @Override
    public void disable() {}
    
    @Override
    public void enable() {}
    
    @Override
    public void load(File file) throws ConfigurationException {
        Listeners.register(this);
    }
    
    @Override
    public void unload() {
        Listeners.unregister(this);
    }
    
    @EventHandler
    public void onThunderChange(ThunderChangeEvent e) {
        e.setCancelled(true);
    }
}
