/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.listeners;

import java.util.logging.Level;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.match.MatchStatus;

/**
 *
 * @author Aleksander
 */
public class ObserverListeners implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (this.isObserver(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (this.isObserver(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (this.isObserver((Player) e.getDamager())) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            if (this.isObserver((Player) e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (this.isObserver((Player) e.getWhoClicked())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (this.isObserver(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (this.isObserver(e.getPlayer())) { // this broke the tutorial book :(
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        if (this.isObserver(e.getPlayer())) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPotionSplash(PotionSplashEvent e) {
        if (e.getEntity() instanceof Player) {
            if (this.isObserver((Player) e.getEntity())) {
                e.setCancelled(true);
            }
            for (LivingEntity reciver : e.getAffectedEntities()) {
                if (reciver instanceof Player) {
                    if (this.isObserver((Player) reciver)) {
                        e.getAffectedEntities().remove(reciver);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onVehicleDamage(VehicleDamageEvent e) {
        if (e.getAttacker() instanceof Player) {
            if (this.isObserver((Player) e.getAttacker())) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onVehicleDestroy(VehicleDestroyEvent e) {
        if (e.getAttacker() instanceof Player) {
            if (this.isObserver((Player) e.getAttacker())) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent e) {
        if (e.getEntered() instanceof Player) {
            if (this.isObserver((Player) e.getEntered())) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onVehicleEntityCollision(VehicleEntityCollisionEvent e) {
        if (e.getEntity() instanceof Player) {
            if (this.isObserver((Player) e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }
    
    private boolean isObserver(Player bukkitPlayer) {
        pl.shg.arcade.api.human.Player player = Arcade.getServer().getPlayer(bukkitPlayer.getUniqueId());
        if (Arcade.getMatches().getStatus() != MatchStatus.PLAYING) {
            return true;
        } else if (player != null) {
            return player.isObserver();
        } else {
            Log.log(Level.SEVERE, "Gracz " + bukkitPlayer.getName() + " nie istnieje jako Player, wyrzucam.");
            bukkitPlayer.kickPlayer("Drogi graczu; napotkalismy blad z Twoim uzytkownikiem w grze.\n"
                    + "Prosze zaloguj sie ponownie na serwer.");
            return true;
        }
    }
}
