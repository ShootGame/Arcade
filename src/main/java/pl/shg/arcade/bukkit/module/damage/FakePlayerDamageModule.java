/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.damage;

import java.io.File;
import java.util.Date;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.shg.arcade.api.configuration.ConfigurationException;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class FakePlayerDamageModule extends Module implements BListener {
    public FakePlayerDamageModule() {
        super(new Date(2015, 5, 3), "fake-player-damage", Version.valueOf("1.0"));
        this.getDocs().setDescription("Anuluje uderzenia na graczu wykonane " +
                "przez innego gracza. Gracz nie traci życia, lecz zostaje uderzony.");
        this.deploy(true);
    }
    
    @Override
    public void disable() {
        Listeners.unregister(this);
    }
    
    @Override
    public void enable() {
        Listeners.register(this);
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        
    }
    
    @Override
    public void unload() {
        
    }
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            e.setDamage(0);
        }
    }
}
