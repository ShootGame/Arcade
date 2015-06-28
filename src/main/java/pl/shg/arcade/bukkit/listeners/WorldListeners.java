/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.commons.server.ArcadeMatchStatus;

/**
 *
 * @author Aleksander
 */
public class WorldListeners implements Listener {
    @EventHandler
    public void onBlockBurn(BlockBurnEvent e) {
        if (this.cancel()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockDispense(BlockDispenseEvent e) {
        if (this.cancel()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockFade(BlockFadeEvent e) {
        if (this.cancel()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockForm(BlockFormEvent e) {
        if (this.cancel()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockFromTo(BlockFromToEvent e) {
        if (this.cancel()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockGrow(BlockGrowEvent e) {
        if (this.cancel()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent e) {
        if (this.cancel()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBlockSpreadEvent(BlockSpreadEvent e) {
        if (this.cancel()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onLeavesDeacy(LeavesDecayEvent e) {
        if (this.cancel()) {
            e.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onThunderChangeEvent(ThunderChangeEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onWeatherChangeEvent(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
    
    private boolean cancel() {
        return Arcade.getMatches().getStatus() != ArcadeMatchStatus.RUNNING;
    }
}
