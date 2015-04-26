/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.module;

import java.io.File;
import java.util.Date;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class RageModule extends Module implements BListener {
    public static final int DAMAGE = 1000;
    
    public RageModule() {
        super(new Date(2014, 11, 8), "rage", "1.0");
        this.getDocs().setDescription("Ten moduł umożliwia dodanie zabijania " +
                "na dotyk. Aby zabić gracza na w tym module, wystaczy go " +
                "tylko dotknąć łapką, lub strzelić w niego strzałą.");
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
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) {
            return;
        }
        
        if (e.getDamager() instanceof Player) {
            if (((Player) e.getDamager()).isDead()) {
                e.setCancelled(true);
            } else {
                e.setDamage(DAMAGE);
            }
        } else if (e.getDamager() instanceof Arrow && ((Arrow) e.getDamager()).getShooter() instanceof Player) {
            e.setDamage(DAMAGE);
        }
    }
}
