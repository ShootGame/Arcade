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
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class NoDeathDropsModule extends Module implements BListener {
    private boolean ignoreObservers;
    
    public NoDeathDropsModule() {
        super(new Date(2015, 4, 19), "no-death-drops", "1.0");
        this.getDocs().setDescription("Ten moduł wyłącza wyrzucanie przedmiotów z graczy po ich śmierci.");
        this.deploy(true);
    }
    
    @Override
    public void disable() {}
    
    @Override
    public void enable() {}
    
    @Override
    public void load(File file) throws ConfigurationException {
        Listeners.register(this);
        this.ignoreObservers = Config.getValueBoolean(Config.get(file), this, "ignore-observers");
    }
    
    @Override
    public void unload() {
        Listeners.unregister(this);
    }
    
    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        if (e.getDeathMessage() != null && !e.getDeathMessage().endsWith(" died")) {
            e.setKeepInventory(true);
        } else {
            e.setKeepInventory(!this.ignoreObservers);
        }
    }
}
