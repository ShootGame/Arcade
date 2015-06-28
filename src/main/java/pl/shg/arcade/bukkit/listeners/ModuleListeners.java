/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.listeners;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.event.EventSubscribtion;
import pl.shg.arcade.api.event.Priority;
import pl.shg.arcade.api.module.ModuleLoadEvent;
import pl.shg.arcade.api.module.ModuleUnloadEvent;
import pl.shg.arcade.bukkit.plugin.ArcadeBukkitPlugin;

/**
 *
 * @author Aleksander
 */
public class ModuleListeners implements EventListener {
    @EventSubscribtion(event = ModuleLoadEvent.class, priority = Priority.MONITOR)
    public void handleModuleLoad(ModuleLoadEvent e) {
        if (e.getModule() instanceof Listener) {
            this.getPlugin().getServer().getPluginManager().registerEvents((Listener) e.getModule(), this.getPlugin());
        }
    }
    
    @EventSubscribtion(event = ModuleUnloadEvent.class, priority = Priority.MONITOR)
    public void handleModuleUnload(ModuleUnloadEvent e) {
        if (e.getModule() instanceof Listener) {
            HandlerList.unregisterAll((Listener) e.getModule());
        }
    }
    
    private Plugin getPlugin() {
        return ArcadeBukkitPlugin.getPlugin();
    }
}
