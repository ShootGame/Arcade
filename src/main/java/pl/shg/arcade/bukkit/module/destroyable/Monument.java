/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.destroyable;

import org.bukkit.Location;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.Sound;
import pl.shg.arcade.api.event.Event;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.location.BlockLocation;
import pl.shg.arcade.api.location.GameableBlock;
import pl.shg.arcade.api.module.ModuleMessage;
import pl.shg.arcade.api.team.ObserverTeamBuilder;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.text.Color;
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
        if (this.isDestroyed()) {
            return true;
        } else if (player.getTeam().equals(this.getDestroyable().getOwner())) {
            player.sendError("Nie mozesz niszczyc " + this.getDestroyable().getName() + " swojej druzyny.");
            return false;
        }
        return this.destroy(player);
    }
    
    @Override
    public boolean canInteract(Player player, Material item) {
        if (this.isDestroyed()) {
            return true;
        } else if (player.getTeam().equals(this.getDestroyable().getOwner())) {
            player.sendError("Nie mozesz ingerowac w " + this.getDestroyable().getName() + " swojej druzyny.");
        }
        return true;
    }
    
    public boolean destroy(Player player) {
        // handled with every touch on the monument
        MonumentTouchEvent event = new MonumentTouchEvent(this, player);
        Event.callEvent(event);
        
        if (!event.isCancel()) {
            // the touch is not canceled and a piece can be broke
            this.setDestroyer(event.getPlayer().getTeam());
            Location location = BukkitLocation.valueOf(event.getMonument().getBlock().getLocation());
            location.getBlock().setType(org.bukkit.Material.AIR);
            
            int objective = (Integer) this.destroyable.getSettingValue(Destroyable.Setting.OBJECTIVE);
            boolean finish = objective <= this.destroyable.getPercent() + 0.01;
            
            if (finish) {
                Event.callEvent(new MonumentDestroyedEvent(this, player));
                this.destroyable.destroy(player);
            } else {
                ScoreMode mode = ScoreMode.valueOf(this.destroyable.getSettingValue(Destroyable.Setting.MODE).toString());
                
                if (mode != null) {
                    switch (mode) {
                        case PERCENTS:
                        case STATIC:
                            this.destroyable.setStatus(DestroyStatus.TOUCHED);
                            break;
                        case STATIC_SILENT:
                            this.destroyable.setStatus(DestroyStatus.TOUCHED_SILENT);
                            break;
                    }
                }
            }
            
            this.broadcast(this, !finish);
            return true;
        }
        return false;
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
    
    private void broadcast(Monument monument, boolean silent) {
        String monumentName = Color.UNDERLINE + monument.getDestroyable().getDisplayName() + Color.RESET;
        
        for (Player online : Arcade.getServer().getConnectedPlayers()) {
            Team team = online.getTeam();
            
            // if the player is observer
            if (team.getID().equals(ObserverTeamBuilder.getTeamID())) {
                if (silent) {
                    monumentName = "kawalek " + monumentName;
                }
                
                ModuleMessage.OBSERVER.player(online).sound(Sound.OBJECTIVE).type(ModuleMessage.Type.S_OBJECTIVE).send(
                        "%s" + Color.RESET + " zniszczyl %s %s" + Color.RESET + " (%s bloków).",
                        
                        monument.getDestroyer().getDisplayName(),
                        monumentName,
                        monument.getDestroyable().getOwner().getDisplayName(),
                        monument.getDestroyable().getMonuments().size()
                );
            }
            
            // if the player is the same team as the destroyer
            else if (team.equals(monument.getDestroyer())) {
                if (silent) {
                    monumentName = "Kawalek " + monumentName;
                }
                
                ModuleMessage.ALLY.player(online).sound(Sound.OBJECTIVE_SCORED).type(ModuleMessage.Type.S_OBJECTIVE).send(
                        "%s wroga %s" + Color.RESET + " zostal zniszczony (%s bloków).",
                        
                        monumentName,
                        monument.getDestroyable().getOwner().getDisplayName(),
                        monument.getDestroyable().getMonuments().size()
                );
            }
            
            // if silent - stop and go to the next interation
            else if (silent) {
                continue;
            }
            
            // if the player in the monument's owner team
            else if (team.equals(monument.getDestroyable().getOwner())) {
                ModuleMessage.ENEMY.player(online).sound(Sound.OBJECTIVE_LOST).type(ModuleMessage.Type.E_OBJECTIVE).send(
                        "Twój %s zostal zniszczony przez %s" + Color.RESET + " (%s bloków).",
                        
                        monumentName,
                        monument.getDestroyer().getDisplayName(),
                        monument.getDestroyable().getMonuments().size()
                );
            }
            
            // if the player in the other team
            else {
                ModuleMessage.TEAM.player(online).sound(Sound.OBJECTIVE).type(ModuleMessage.Type.S_OBJECTIVE).send(
                        "%s " + Color.RESET + " zniszczyl %s %s" + Color.RESET + " (%s bloków).",
                        
                        monument.getDestroyer().getDisplayName(),
                        monumentName,
                        monument.getDestroyable().getOwner().getDisplayName(),
                        monument.getDestroyable().getMonuments().size()
                );
            }
        }
    }
}
