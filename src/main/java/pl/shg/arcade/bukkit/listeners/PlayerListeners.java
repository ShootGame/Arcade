/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.listeners;

import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.human.PlayerRespawnMatchEvent;
import pl.shg.arcade.api.kit.KitType;
import pl.shg.arcade.api.location.Spawn;
import pl.shg.arcade.api.text.Color;
import pl.shg.arcade.bukkit.BukkitLocation;
import pl.shg.arcade.bukkit.BukkitServer;
import pl.shg.arcade.bukkit.plugin.ArcadeBukkitPlugin;
import pl.shg.commons.server.ArcadeMatchStatus;

/**
 *
 * @author Aleksander
 */
public class PlayerListeners implements Listener {
    private final BukkitServer server;
    private final Random random;
    
    public PlayerListeners(BukkitServer server) {
        this.server = server;
        this.random = new Random();
    }
    
    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        String message = e.getMessage();
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        ArcadeMatchStatus matchStatus = Arcade.getMatches().getStatus();
        
        if (matchStatus != ArcadeMatchStatus.RUNNING || Arcade.getTeams().getTeams().size() == 1) {
            Arcade.getTeams().getObservers().getChat().sendMessage(player, message);
        } else {
            player.getTeam().getChat().sendMessage(player, message);
        }
    }
    
    @EventHandler
    public void onAsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent e) {
        if (Arcade.getMatches().getStatus() == ArcadeMatchStatus.RESTARTING) {
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST);
            e.setKickMessage(Color.RED + "Serwer jest teraz restartowany...");
        }
        
        if (this.server.isFull()) {
            boolean allowed = false;
            // TODO Mongo query to check if the player is allowed to join this server
            
            if (!allowed) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL,
                        Color.RED + "Ten serwer jest obecnie pelny. Spróbuj " +
                        "polaczyc sie ponownie pózniej, lub wybierz inny " +
                        "serwer. Tylko ranga " + Color.GOLD + Color.BOLD +
                        "VIP" + Color.RESET + Color.RED + " moze dolaczac " +
                        "na pelne serwery."
                );
            }
        }
    }
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.Player) {
            Player entity = Arcade.getServer().getPlayer(e.getEntity().getUniqueId());
            if (!entity.getTeam().isFrendlyFire()) {
                return;
            }
            Player damager = null;
            Projectile projectile = null;
            
            if (e.getDamager() instanceof org.bukkit.entity.Player) { // Player vs player direct
                damager = Arcade.getServer().getPlayer(e.getDamager().getUniqueId());
            } else if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager())
                    .getShooter() instanceof org.bukkit.entity.Player) { // Arrows, snowballs, etc...
                projectile = (Projectile) e.getDamager();
                damager = Arcade.getServer().getPlayer(((org.bukkit.entity.Player) projectile.getShooter()).getUniqueId());
            }
            
            if (damager != null && entity.getTeam().equals(damager.getTeam())) {
                e.setCancelled(true);
            }
            if (projectile != null) {
                projectile.setBounce(true);
            }
        }
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getDeathMessage() == null) {
            return;
        } else if (e.getDeathMessage().endsWith(" died")) {
            e.setDeathMessage(null);
        }
        
        if (Arcade.getMatches().getStatus() != ArcadeMatchStatus.RUNNING) {
            e.getDrops().clear();
        }
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        final Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        List<Spawn> spawns = this.getSpawnLocations(player);
        Spawn spawn = spawns.get(this.random.nextInt(spawns.size()));
        
        PlayerRespawnMatchEvent event = new PlayerRespawnMatchEvent(player, spawn);
        Event.callEvent(event);
        
        e.setRespawnLocation(BukkitLocation.valueOf(event.getSpawn()));
        
        if (!event.isCancel()) {
            Bukkit.getScheduler().runTaskLater(ArcadeBukkitPlugin.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    if (player == null) {
                        return;
                    } else if (player.isObserver()) {
                        Arcade.getPlayerManagement().setAsObserver(player, true, true, true);
                    } else {
                        Arcade.getPlayerManagement().setAsPlayer(player, KitType.RESPAWN, false, false, false);
                    }
                }
            }, 1L);
        }
    }
    
    private List<Spawn> getSpawnLocations(Player player) {
        if (Arcade.getMatches().getStatus() == ArcadeMatchStatus.RUNNING) {
            return player.getTeam().getSpawns();
        } else {
            return Arcade.getTeams().getObservers().getSpawns();
        }
    }
}
