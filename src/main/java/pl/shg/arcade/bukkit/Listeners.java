/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import pl.shg.arcade.bukkit.plugin.ArcadeBukkitPlugin;

/**
 *
 * @author Aleksander
 */
public class Listeners {
    private static final PluginManager manager = Bukkit.getPluginManager();
    private static final Plugin plugin = ArcadeBukkitPlugin.getPlugin();
    
    public static void register(Listener listener) {
        Validate.notNull(listener, "listener can not be null");
        manager.registerEvents(listener, plugin);
    }
    
    public static void register(Listener... listeners) {
        Validate.notNull(listeners, "listeners can not be null");
        for (Listener listener : listeners) {
            register(listener);
        }
    }
    
    public static void unregister(Listener listener) {
        Validate.notNull(listener, "listener can not be null");
        HandlerList.unregisterAll(listener);
    }
    
    public static void unregister(Listener... listeners) {
        Validate.notNull(listeners, "listeners can not be null");
        for (Listener listener : listeners) {
            unregister(listener);
        }
    }
}
