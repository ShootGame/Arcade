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
import java.util.UUID;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;

/**
 *
 * @author Aleksander
 */
public class AutoRespawnModule extends Module implements BListener {
    public static final long RESPAWN_MILLIS = 500L;
    public static final long UPDATE_TICKS = 5;
    
    private final HashMap<UUID, Long> respawn = new HashMap<>();
    
    public AutoRespawnModule() {
        super(new Date(2015, 4, 26), "auto-respawn", "1.0");
        this.getDocs().setDescription("Moduł ten dodaje auto-respawnowanie graczy. " +
                "Każdy gracz po śmierci zostanie automatycznie odrodzony.");
        this.deploy(true);
    }
    
    @Override
    public void disable() {
        Listeners.unregister(this);
    }

    @Override
    public void enable() {
        Listeners.register(this);
        Arcade.getServer().getScheduler().runSync(new Task(), AutoRespawnModule.UPDATE_TICKS);
    }

    @Override
    public void load(File file) throws ConfigurationException {
        
    }

    @Override
    public void unload() {
        
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        respawn.put(e.getEntity().getUniqueId(), System.currentTimeMillis());
    }
    
    private class Task implements Runnable {
        @Override
        public void run() {
            for (UUID id : AutoRespawnModule.this.respawn.keySet()) {
                long millis = AutoRespawnModule.this.respawn.get(id);
                if (millis + AutoRespawnModule.RESPAWN_MILLIS > System.currentTimeMillis()) {
                    Player player = Arcade.getServer().getPlayer(id);
                    if (player != null) {
                        player.respawn();
                    }
                    AutoRespawnModule.this.respawn.remove(id);
                }
            }
        }
    }
}
