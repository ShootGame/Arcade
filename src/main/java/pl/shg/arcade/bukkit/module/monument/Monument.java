/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.monument;

import org.bukkit.Bukkit;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Material;
import pl.shg.arcade.api.chat.ActionMessageType;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.Block;
import pl.shg.arcade.api.map.GameableBlock;
import pl.shg.arcade.api.map.Location;
import pl.shg.arcade.api.module.ScoreboardScore;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.util.Validate;
import pl.shg.arcade.bukkit.ScoreboardManager;

/**
 *
 * @author Aleksander
 */
public class Monument extends GameableBlock {
    private final Objective objective;
    private Team destroyed;
    
    public Monument(Block block, Objective objective) {
        super(block);
        
        Validate.notNull(objective, "objective can not be null");
        this.objective = objective;
    }
    
    @Override
    public boolean canBreak(Player player) {
        if (player.getTeam().equals(this.getOwner())) {
            player.sendError("Nie mozesz ingerowac w monument swojej druzyny.");
            return false;
        } else {
            this.destroy(player);
            return true;
        }
    }
    
    @Override
    public boolean canInteract(Player player, Material item) {
        if (player.getTeam().equals(this.getOwner())) {
            player.sendError("Nie mozesz ingerowac w monument swojej druzyny.");
        }
        return true;
    }
    
    public void destroy(Player player) {
        this.setDestroyed(player.getTeam());
        Location location = this.getBlock().getLocation();
        org.bukkit.Location bLocation = new org.bukkit.Location(
                Bukkit.getWorld(Arcade.getMaps().getCurrentMap().getName()),
                location.getX(),
                location.getY(),
                location.getZ());
        bLocation.getBlock().setType(org.bukkit.Material.AIR);
        
        if (this.getObjective().isDestroyed()) {
            this.updateScoreboard();
            this.getObjective().getModule().updateObjectives();
            
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < this.getObjective().getMonuments().size(); i++) {
                Monument monument = this.getObjective().getMonuments().get(i);
                builder.append(monument.getDestroyed().getDisplayName()).append(Color.AQUA).append(", ");
            }
            String by = builder.toString().substring(0, builder.toString().length() - 2);
            
            for (Player online : Arcade.getServer().getOnlinePlayers()) {
                if (online.getTeam().equals(player.getTeam())) {
                    // send to destroyers team
                    online.sendActionMessage(ActionMessageType.SUCCESS,
                            this.getDestroyed().getDisplayName() + Color.DARK_GREEN +" znisczyla " +
                            Color.DARK_AQUA + this.getObjective().getDisplayName() + Color.DARK_GREEN +
                            " druzyny " + this.getOwner().getDisplayName() + Color.DARK_GREEN + " (" +
                            this.getObjective().getMonuments().size() + " bloków).");
                } else if (online.getTeam().equals(this.getOwner())) {
                    // send to owner of this objective
                    online.sendActionMessage(ActionMessageType.ERROR, "Twój " +
                            Color.DARK_AQUA + this.getObjective().getDisplayName() + Color.DARK_RED +
                            " zostal zniszczony przez " + by + Color.DARK_RED + " (" +
                            this.getObjective().getMonuments().size() + " bloków).");
                } else {
                    // send to other players like other teams (if 3 or more is available) and observers
                    online.sendActionMessage(ActionMessageType.INFO,
                            this.getDestroyed().getDisplayName() + Color.DARK_AQUA +" znisczyla " +
                            Color.DARK_AQUA + this.getObjective().getDisplayName() + Color.YELLOW +
                            " druzyny " + this.getOwner().getDisplayName() + Color.YELLOW + " (" +
                            this.getObjective().getMonuments().size() + " bloków).");
                }
            }
        } else {
            player.getTeam().getChat().sendActionMessage(ActionMessageType.SUCCESS, player.getDisplayName() +
                    Color.DARK_GREEN + " zniszczyl kawalek " + this.getObjective().getDisplayName() +
                    Color.DARK_GREEN + " druzyny " + this.getOwner().getDisplayName() + Color.DARK_GREEN + ".");
        }
    }
    
    public Team getDestroyed() {
        return this.destroyed;
    }
    
    public Objective getObjective() {
        return this.objective;
    }
    
    public Team getOwner() {
        return this.getObjective().getOwner();
    }
    
    public boolean isDestroyed() {
        return this.destroyed != null;
    }
    
    public void setDestroyed(Team team) {
        this.destroyed = team;
    }
    
    private void updateScoreboard() {
        ScoreboardScore score = ScoreboardManager.Sidebar.getScore(this.getObjective().getScoreboardID());
        score.setPrefix("  " + Color.DARK_RED);
        ScoreboardManager.Sidebar.updateScoreboard();
    }
}
