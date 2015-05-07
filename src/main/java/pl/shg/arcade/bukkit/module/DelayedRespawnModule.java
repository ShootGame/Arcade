/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.event.PlayerRespawnMatchEvent;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.kit.KitType;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.Spawn;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class DelayedRespawnModule extends Module implements BListener {
    public static int seconds = 10;
    private final Map<UUID, Long> dead = new HashMap<>();
    private EventListener respawnListener;
    
    public DelayedRespawnModule() {
        super(new Date(2015, 4, 25), "delayed-respawn", "1.0");
    }
    
    @Override
    public void disable() {
        Event.unregisterListener(this.respawnListener);
    }
    
    @Override
    public void enable() {
        this.respawnListener = new PlayerRespawnMatch();
        Event.registerListener(this.respawnListener);
        
        Arcade.getServer().getScheduler().run(new Runnable() {
            private final DelayedRespawnModule module = (DelayedRespawnModule) Module.of(DelayedRespawnModule.class);
            private final int defaults = this.module.getSeconds() * 1000;
            
            @Override
            public void run() {
                for (UUID uuid : this.module.getDead().keySet()) {
                    long time = this.module.getDead().get(uuid);
                    if ((time + this.defaults) <= System.currentTimeMillis()) {
                        this.module.getDead().remove(uuid);
                        this.module.respawn(uuid);
                    }
                }
            }
        });
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        Listeners.register(this);
        FileConfiguration config = Config.get(file);
        seconds = Config.getValueInt(config, this, "seconds");
    }
    
    @Override
    public void unload() {
        Listeners.unregister(this);
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = Arcade.getServer().getPlayer(e.getEntity().getUniqueId());
        if (player.isObserver()) {
            return;
        }
        
        this.dead.put(player.getUUID(), System.currentTimeMillis());
    }
    
    public Map<UUID, Long> getDead() {
        return this.dead;
    }
    
    public int getSeconds() {
        return seconds;
    }
    
    public void respawn(UUID uuid) {
        Player player = Arcade.getServer().getPlayer(uuid);
        Arcade.getPlayerManagement().setAsPlayer(player, KitType.RESPAWN, false, false, false);
        player.sendMessage("Zostales/as odrodzony/a po " + seconds + " sekundach.");
    }
    
    private class PlayerRespawnMatch implements EventListener {
        @Override
        public Class<? extends Event> getEvent() {
            return PlayerRespawnMatchEvent.class;
        }
        
        @Override
        public void handle(Event event) {
            PlayerRespawnMatchEvent e = (PlayerRespawnMatchEvent) event;
            if (!e.getPlayer().isObserver()) {
                e.setCancel(true); // cancel this event, so play will not give kits, etc...
                e.setSpawn((Spawn) e.getPlayer().getLocation()); // respawn player in the death location
                
                e.getPlayer().sendMessage("Zostaniesz odrodzony/a za " + DelayedRespawnModule.seconds + " sekund...");
                this.dead(e.getPlayer());
            }
        }
        
        private void dead(Player player) {
            
        }
    }
}
