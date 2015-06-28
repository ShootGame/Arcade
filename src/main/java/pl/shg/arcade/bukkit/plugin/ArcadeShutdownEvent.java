/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.plugin;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.shg.arcade.PluginImpl;
import pl.shg.arcade.bukkit.BukkitServer;

/**
 *
 * @author Aleksander
 */
public class ArcadeShutdownEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    
    private final PluginImpl api;
    private final ArcadeBukkitPlugin plugin;
    private final BukkitServer server;
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public ArcadeShutdownEvent(PluginImpl api, ArcadeBukkitPlugin plugin, BukkitServer server) {
        this.api = api;
        this.plugin = plugin;
        this.server = server;
    }
    
    public PluginImpl getAPI() {
        return this.api;
    }
    
    public ArcadeBukkitPlugin getPlugin() {
        return this.plugin;
    }
    
    public BukkitServer getServer() {
        return this.server;
    }
}
