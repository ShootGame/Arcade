/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.location.GameableBlock;
import pl.shg.arcade.api.location.Location;
import pl.shg.arcade.api.match.MatchStatus;

/**
 *
 * @author Aleksander
 */
public class GameableBlockListeners implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        if (player.isObserver() || Arcade.getMatches().getStatus() != MatchStatus.PLAYING) {
            return;
        }
        
        org.bukkit.Location bLoc = e.getBlock().getLocation();
        GameableBlock block = GameableBlock.getBlock(new Location(
                bLoc.getBlockX(),
                bLoc.getBlockY(),
                bLoc.getBlockZ()
        ));
        
        if (block != null) {
            if (!block.canBreak(player)) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        if (player.isObserver() || Arcade.getMatches().getStatus() != MatchStatus.PLAYING) {
            return;
        }
        
        org.bukkit.Location bLoc = e.getBlock().getLocation();
        GameableBlock block = GameableBlock.getBlock(new Location(
                bLoc.getBlockX(),
                bLoc.getBlockY(),
                bLoc.getBlockZ()
        ));
        
        if (block != null) {
            Material material = null;
            if (e.getItemInHand() != null && e.getItemInHand().getType().getId() != 0) {
                material = new Material(e.getItemInHand().getType().getId());
            }
            
            if (!block.canPlace(player, material)) {
                e.setCancelled(true);
            }
        }
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        if (player.isObserver() || Arcade.getMatches().getStatus() != MatchStatus.PLAYING || e.getClickedBlock() == null) {
            return;
        }
        
        org.bukkit.Location bLoc = e.getClickedBlock().getLocation();
        GameableBlock block = GameableBlock.getBlock(new Location(
                bLoc.getBlockX(),
                bLoc.getBlockY(),
                bLoc.getBlockZ()
        ));
        
        if (block != null) {
            Material material = null;
            if (e.getMaterial() != null && e.getMaterial().getId() != 0) {
                material = new Material(e.getMaterial().getId());
            }
            
            if (!block.canInteract(player, material)) {
                e.setCancelled(true);
            }
        }
    }
}
