/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import pl.shg.commons.bukkit.UserUtils;
import pl.shg.commons.helpers.KillHelper;
import pl.shg.commons.mongo.Database;

/**
 *
 * @author Aleksander
 */
public class DatabaseListeners implements Listener {
    public void init(Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        //Event.registerListener(null);
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerKill(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        KillHelper helper = (KillHelper) Database.getHelper(KillHelper.class);
        
        if (killer != null && !e.getEntity().equals(killer)) {
            helper.addKill(UserUtils.getUser(e.getEntity()), UserUtils.getUser(killer));
        }
    }
}
