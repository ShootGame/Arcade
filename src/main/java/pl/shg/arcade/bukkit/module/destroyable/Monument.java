/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import org.bukkit.Location;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.BlockLocation;
import pl.shg.arcade.api.map.GameableBlock;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.bukkit.BukkitLocation;

/**
 *
 * @author Aleksander
 */
public class Monument extends GameableBlock {
    private final BlocksDestroyable destroyable;
    private Team destroyer;
    
    public Monument(BlockLocation block, BlocksDestroyable destroyable) {
        super(block.getBlock());
        this.destroyable = destroyable;
    }
    
    @Override
    public boolean canBreak(Player player) {
        if (player.getTeam().equals(this.getDestroyable().getTeam())) {
            player.sendError("Nie mozesz ingerowac w monument swojej druzyny.");
            return false;
        } else {
            this.destroy(player);
            return true;
        }
    }
    
    @Override
    public boolean canInteract(Player player, Material item) {
        if (player.getTeam().equals(this.getDestroyable().getTeam())) {
            player.sendError("Nie mozesz ingerowac w monument swojej druzyny.");
        }
        return true;
    }
    
    public void destroy(Player player) {
        MonumentDestroyEvent event = new MonumentDestroyEvent(this, player);
        Event.callEvent(event);
        
        if (!event.isCancel()) {
            this.setDestroyer(event.getPlayer().getTeam());
            Location location = BukkitLocation.valueOf(event.getMonument().getBlock().getLocation());
            location.getBlock().setType(org.bukkit.Material.AIR);
            
            Event.callEvent(new MonumentDestroyedEvent(this, player));
            if ((Integer) this.destroyable.getSettingValue(Destroyable.Setting.OBJECTIVE) <= this.destroyable.getPercent()) {
                this.destroyable.destroy(player);
            }
        }
    }
    
    public BlocksDestroyable getDestroyable() {
        return this.destroyable;
    }
    
    public boolean isDestroyed() {
        return this.getDestroyer() != null;
    }
    
    public Team getDestroyer() {
        return this.destroyer;
    }
    
    public void setDestroyer(Team destroyer) {
        this.destroyer = destroyer;
    }
}
