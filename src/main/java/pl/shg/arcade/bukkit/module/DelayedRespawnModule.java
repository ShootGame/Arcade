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
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.team.kit.KitType;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class DelayedRespawnModule extends Module implements BListener {
    private final Map<UUID, Long> dead = new HashMap<>();
    private int seconds = 10;
    
    public DelayedRespawnModule() {
        super(new Date(2015, 4, 25), "delayed-respawn", "1.0");
    }
    
    @Override
    public void disable() {}
    
    @Override
    public void enable() {
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
        this.seconds = Config.getValueInt(config, this, "seconds");
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
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        if (player.isObserver()) {
            return;
        }
        
        player.sendMessage("Zostaniesz odrodzony/a za " + this.seconds + " sekund...");
        this.dead(player, e.getPlayer());
    }
    
    public void dead(Player player, org.bukkit.entity.Player bukkit) {
        bukkit.setGameMode(GameMode.CREATIVE);
    }
    
    public Map<UUID, Long> getDead() {
        return this.dead;
    }
    
    public int getSeconds() {
        return this.seconds;
    }
    
    public void respawn(UUID uuid) {
        Player player = Arcade.getServer().getPlayer(uuid);
        Arcade.getPlayerManagement().setAsPlayer(player, KitType.RESPAWN, false, false);
        player.sendMessage("Zostales/as odrodzony/a po " + this.seconds + " sekundach.");
    }
}
