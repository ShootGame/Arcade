/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import java.io.File;
import java.util.Date;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.event.EventListener;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.region.Region;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.bukkit.BukkitLocation;
import pl.shg.commons.util.Fireworks;

/**
 *
 * @author Aleksander
 */
public class DestroyableFireworksModule extends Module {
    private EventListener listener;
    
    public DestroyableFireworksModule() {
        super(new Date(2015, 5, 14), "destroyable-fireworks", "1.0");
        this.deploy(true);
    }
    
    @Override
    public void disable() {
        Event.unregisterListener(this.listener);
    }
    
    @Override
    public void enable() {
        this.listener = new DestroyableDestroy();
        Event.registerListener(this.listener);
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        
    }
    
    @Override
    public void unload() {
        
    }
    
    private void createFirework(Location bukkitLocation, Team team) {
        Fireworks.create(bukkitLocation, FireworkEffect.builder()
                .with(FireworkEffect.Type.BURST)
                .withFlicker()
                .withColor(Color.fromBGR(0, 0, 0))
                .build(), 0);
        
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (online.getLocation().distance(bukkitLocation) > 64) {
                online.playSound(online.getLocation(), Sound.FIREWORK_LARGE_BLAST2, 0.75F, 1F);
                online.playSound(online.getLocation(), Sound.FIREWORK_TWINKLE2, 0.75F, 1F);
            }
        }
    }
    
    private class DestroyableDestroy implements EventListener {
        @Override
        public Class<? extends Event> getEvent() {
            return DestroyableDestroyedEvent.class;
        }
        
        @Override
        public void handle(Event event) {
            DestroyableDestroyedEvent e = (DestroyableDestroyedEvent) event;
            
            if (e.getDestroyable() instanceof BlocksDestroyable) {
                List<Monument> monuments = ((BlocksDestroyable) e.getDestroyable()).getMonuments();
                Location bukkitLocation = BukkitLocation.valueOf(monuments.get(0).getBlock().getLocation());
                DestroyableFireworksModule.this.createFirework(bukkitLocation, e.getPlayer().getTeam());
            } else if (e.getDestroyable() instanceof RegionDestroyable) {
                Region region = ((RegionDestroyable) e.getDestroyable()).getRegion();
                Location bukkitLocaiton = BukkitLocation.valueOf(region.getMax()); // TODO fix this
                DestroyableFireworksModule.this.createFirework(bukkitLocaiton, e.getPlayer().getTeam());
            }
        }
    }
}
