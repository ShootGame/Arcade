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
import org.bukkit.World;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.Spawn;
import pl.shg.arcade.api.map.team.kit.KitType;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.util.Validate;
import pl.shg.arcade.bukkit.BukkitPlayer;
import pl.shg.arcade.bukkit.BukkitServer;
import pl.shg.arcade.bukkit.plugin.ArcadeBukkitPlugin;

/**
 *
 * @author Aleksander
 */
public class PlayerListeners implements Listener {
    private final BukkitServer server;
    private final Random random;
    
    public PlayerListeners(BukkitServer server) {
        Validate.notNull(server, "server can not be null");
        this.server = server;
        this.random = new Random();
    }
    
    @EventHandler
    public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        String message = e.getMessage();
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        MatchStatus matchStatus = Arcade.getMatches().getStatus();
        
        if (matchStatus != MatchStatus.PLAYING) {
            Arcade.getTeams().getObservers().getChat().sendMessage(player, message);
        } else {
            player.getTeam().getChat().sendMessage(player, message);
        }
    }
    
    @EventHandler
    public void onAsyncPlayerLoginEvent(AsyncPlayerPreLoginEvent e) {
        if (Arcade.getMatches().getStatus() == MatchStatus.NOTHING) {
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST);
            e.setKickMessage(Color.RED + "Serwer jest teraz restartowany...");
        }
    }
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof org.bukkit.entity.Player) {
            Player entity = Arcade.getServer().getPlayer(e.getEntity().getUniqueId());
            
            if (e.getDamager() instanceof org.bukkit.entity.Player) { // Player vs player direct
                Player damager = Arcade.getServer().getPlayer(e.getDamager().getUniqueId());
                if (entity.getTeam().isFrendlyFire()) {
                    if (entity.getTeam().equals(damager.getTeam())) {
                        e.setCancelled(true);
                    }
                }
            } else if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager())
                    .getShooter() instanceof org.bukkit.entity.Player) { // Arrows, snowballs, etc...
                Projectile projectile = (Projectile) e.getDamager();
                Player damager = Arcade.getServer().getPlayer(
                        ((org.bukkit.entity.Player) projectile.getShooter()).getUniqueId());
                if (entity.getTeam().isFrendlyFire()) {
                    if (entity.getTeam().equals(damager.getTeam())) {
                        e.setCancelled(true);
                        projectile.setBounce(true);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        if (e.getDeathMessage().endsWith(" died")) {
            e.setDeathMessage(null);
        }
        
        MatchStatus status = Arcade.getMatches().getStatus();
        if (status != MatchStatus.PLAYING) {
            e.getDrops().clear();
        }
        // TODO xp
    }
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = new BukkitPlayer(e.getPlayer());
        this.server.addPlayer(player);
        player.updateDisplayName();
        
        List<Spawn> spawns = player.getTeam().getSpawns();
        Spawn spawn = spawns.get(this.random.nextInt(spawns.size()));
        World world = Bukkit.getWorld(Arcade.getMaps().getCurrentMap().getName());
        Arcade.getPlayerManagement().setAsObserver(player, true, true);
        e.getPlayer().teleport(new Location(world, spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getYaw(), spawn.getPitch()));
        
        player.setTabList(Arcade.getServer().getGlobalTabList());
    }
    
    @EventHandler
    public void setPlayerKick(PlayerKickEvent e) {
        this.handleLogout(e.getPlayer());
    }
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        this.handleLogout(e.getPlayer());
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        final Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        String world = Arcade.getMaps().getCurrentMap().getName();
        List<Spawn> spawns = player.getTeam().getSpawns();
        Spawn spawn = spawns.get(this.random.nextInt(spawns.size()));
        e.setRespawnLocation(new Location(Bukkit.getWorld(world), spawn.getX(), spawn.getY(), spawn.getZ(),
                spawn.getYaw(), spawn.getPitch()));
        
        Bukkit.getScheduler().runTaskLater(ArcadeBukkitPlugin.getPlugin(), new Runnable() {
            
            @Override
            public void run() {
                if (player.isObserver()) {
                    Arcade.getPlayerManagement().setAsObserver(player, true, false);
                } else {
                    Arcade.getPlayerManagement().setAsPlayer(player, KitType.RESPAWN, false, false);
                }
            }
        }, 20L);
    }
    
    private void handleLogout(org.bukkit.entity.Player bPlayer) {
        try {
            this.server.removePlayer(this.server.getPlayer(bPlayer.getUniqueId()));
        } catch (IllegalArgumentException ex) {
            Log.noteAdmins("Nie udalo sie usunac gracza " + bPlayer + " poniewaz nie istnial",
                    Log.NoteLevel.SEVERE);
        }
    }
}
