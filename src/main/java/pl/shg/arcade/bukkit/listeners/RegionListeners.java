/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.listeners;

import java.util.List;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.filter.Filter;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.location.Block;
import pl.shg.arcade.api.location.Location;
import pl.shg.arcade.api.map.MapManager;
import pl.shg.arcade.api.region.Flag;
import pl.shg.arcade.api.region.Region;
import pl.shg.arcade.bukkit.BukkitLocation;
import pl.shg.commons.server.ArcadeMatchStatus;

/**
 *
 * @author Aleksander
 */
public class RegionListeners implements Listener {
    private final MapManager map = Arcade.getMaps();
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        org.bukkit.Location bukkitLocation = e.getBlock().getLocation();
        Location location = BukkitLocation.convert(bukkitLocation);
        
        List<Region> regions = this.getRegions(bukkitLocation);
        if (regions.isEmpty()) {
            return;
        }
        
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        if (this.isObserver(player)) {
            return;
        }
        for (Region region : regions) {
            if (this.isIn(region, bukkitLocation)) {
                for (Filter filter : region.getFilters()) {
                    if (!filter.canBuild(location, new Material(e.getPlayer().getItemInHand().getTypeId()))) {
                        e.setCancelled(true);
                    }
                }
                
                for (Flag flag : region.getFlags()) {
                    if (!flag.canBreak(player, new Block())) {
                        e.setCancelled(true);
                        if (flag.getMessage() != null) {
                            player.sendMessage(flag.getMessage());
                        }
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        org.bukkit.Location bukkitLocation = e.getBlock().getLocation();
        List<Region> regions = this.getRegions(bukkitLocation);
        if (regions.isEmpty()) {
            return;
        }
        
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        if (this.isObserver(player)) {
            return;
        }
        for (Region region : regions) {
            if (this.isIn(region, bukkitLocation)) {
                for (Flag flag : region.getFlags()) {
                    if (!flag.canPlace(player, new Block())) {
                        e.setCancelled(true);
                        if (flag.getMessage() != null) {
                            player.sendMessage(flag.getMessage());
                        }
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) {
            return;
        }
        org.bukkit.Location bukkitLocation = e.getClickedBlock().getLocation();
        List<Region> regions = this.getRegions(bukkitLocation);
        if (regions.isEmpty()) {
            return;
        }
        
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        if (this.isObserver(player)) {
            return;
        }
        for (Region region : regions) {
            if (this.isIn(region, bukkitLocation)) {
                for (Flag flag : region.getFlags()) {
                    if (!flag.canInteract(player, new Material(bukkitLocation.getBlock().getTypeId()))) {
                        e.setCancelled(true);
                        if (flag.getMessage() != null) {
                            player.sendMessage(flag.getMessage());
                        }
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        org.bukkit.Location bukkitLocation = e.getTo();
        List<Region> regions = this.getRegions(bukkitLocation);
        if (regions.isEmpty()) {
            return;
        }
        
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        if (this.isObserver(player)) {
            return;
        }
        for (Region region : regions) {
            if (this.isIn(region, bukkitLocation)) {
                for (Flag flag : region.getFlags()) {
                    if (!flag.canMove(player)) {
                        org.bukkit.Location from = e.getFrom();
                        from.setX(from.getBlockX() + 0.5);
                        from.setZ(from.getBlockZ() + 0.5);
                        e.getPlayer().teleport(from);
                        
                        if (flag.getMessage() != null) {
                            player.sendMessage(flag.getMessage());
                        }
                    }
                }
            }
        }
    }
    
    private List<Region> getRegions(org.bukkit.Location bukkitLocation) {
        Location location = new Location(bukkitLocation.getX(), bukkitLocation.getY(), bukkitLocation.getZ());
        return this.map.getCurrentMap().getRegions().getRegions(location);
    }
    
    private boolean isIn(Region region, org.bukkit.Location bukkitLocation) {
        return region.isIn(new Location(bukkitLocation.getX(), bukkitLocation.getY(), bukkitLocation.getZ()));
    }
    
    private boolean isObserver(Player player) {
        return player.isObserver() || Arcade.getMatches().getStatus() != ArcadeMatchStatus.RUNNING;
    }
}
