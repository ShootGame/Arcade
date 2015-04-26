/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */

package pl.shg.arcade.bukkit.module;

import java.io.File;
import java.util.Date;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;
import pl.shg.arcade.bukkit.plugin.ArcadeBukkitPlugin;

/**
 *
 * @author Aleksander
 */
public class AutoRespawnModule extends Module implements BListener {
    public AutoRespawnModule() {
        super(new Date(2015, 4, 26), "auto-respawn", "1.0");
        this.getDocs().setDescription("Moduł ten dodaje auto-respawnowanie graczy. " +
                "Każdy gracz po śmierci zostanie automatycznie odrodzony.");
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
    public void onPlayerDeath(PlayerDeathEvent e) {
        final Player player = Arcade.getServer().getPlayer(e.getEntity().getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                player.respawn();
            }
        }.runTaskLater(ArcadeBukkitPlugin.getPlugin(), 12L);
    }
}
