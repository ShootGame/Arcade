/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module;

import java.io.File;
import java.util.Date;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.util.Version;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class NoPvpModule extends Module implements BListener {
    public NoPvpModule() {
        super(new Date(2015, 4, 20), "no-pvp", Version.valueOf("1.0"));
        this.getDocs().setDescription("Ten moduł wyłącza walkę PvP na całej mapie u wszyskich graczy.");
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
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            e.setCancelled(true);
        }
    }
}
