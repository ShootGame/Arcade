/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.BrewingStand;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.block.Furnace;
import org.bukkit.block.Hopper;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.map.team.TeamManager;
import pl.shg.arcade.api.match.MatchManager;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.server.Server;

/**
 *
 * @author Aleksander
 */
public class InventorySpyListeners implements Listener {
    private final MatchManager matches;
    private final Server server;
    private final TeamManager teams;
    
    public InventorySpyListeners() {
        this.matches = Arcade.getMatches();
        this.server = Arcade.getServer();
        this.teams = Arcade.getTeams();
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;
        Player bukkitPlayer = e.getPlayer();
        
        if (this.matches.getStatus() == MatchStatus.ENDING || this.matches.getStatus() == MatchStatus.STARTING
                || this.server.getPlayer(bukkitPlayer.getUniqueId()).isObserver()) {
            Material block = e.getClickedBlock().getType();
            BlockState state = e.getClickedBlock().getState();
            
            Inventory spy = null;
            switch (block) {
                case BREWING_STAND:
                    BrewingStand brewing = (BrewingStand) state;
                    spy = Bukkit.createInventory(bukkitPlayer, InventoryType.BREWING);
                    spy.setContents(brewing.getInventory().getContents());
                    break;
                case LOCKED_CHEST:
                case TRAPPED_CHEST:
                case CHEST:
                    Chest chest = (Chest) state;
                    spy = Bukkit.createInventory(bukkitPlayer, chest.getInventory().getSize());
                    spy.setContents(chest.getInventory().getContents());
                    break;
                case DISPENSER:
                    Dispenser dispenser = (Dispenser) state;
                    spy = Bukkit.createInventory(bukkitPlayer, InventoryType.DISPENSER);
                    spy.setContents(dispenser.getInventory().getContents());
                    break;
                case DROPPER:
                    Dropper dropper = (Dropper) state;
                    spy = Bukkit.createInventory(bukkitPlayer, InventoryType.DROPPER);
                    spy.setContents(dropper.getInventory().getContents());
                    break;
                case FURNACE:
                    Furnace furnace = (Furnace) state;
                    spy = Bukkit.createInventory(bukkitPlayer, InventoryType.FURNACE);
                    spy.setContents(furnace.getInventory().getContents());
                    break;
                case STORAGE_MINECART:
                    StorageMinecart storageChest = (StorageMinecart) state;
                    spy = Bukkit.createInventory(bukkitPlayer, storageChest.getInventory().getSize());
                    spy.setContents(storageChest.getInventory().getContents());
                    break;
                case HOPPER:
                    Hopper hopper = (Hopper) state;
                    spy = Bukkit.createInventory(bukkitPlayer, InventoryType.HOPPER);
                    spy.setContents(hopper.getInventory().getContents());
                    break;
            }
            if (spy != null) {
                bukkitPlayer.openInventory(spy);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Player)) {
            return;
        }
        
        Player targetBukkit = (Player) e.getRightClicked();
        pl.shg.arcade.api.human.Player target = this.server.getPlayer(targetBukkit.getUniqueId());
        Player bukkitPlayer = e.getPlayer();
        pl.shg.arcade.api.human.Player player = this.server.getPlayer(bukkitPlayer.getUniqueId());
        if (this.matches.getStatus() == MatchStatus.ENDING ||
                (player.isObserver() && !this.server.getPlayer(target.getUUID()).isObserver())) {
            Inventory inv = targetBukkit.getInventory();
            inv = Bukkit.createInventory(bukkitPlayer, inv.getSize(), Color.GRAY + "Ekwipunek " + target.getDisplayName());
            inv.setContents(inv.getContents());
            bukkitPlayer.openInventory(inv);
        }
    }
}
