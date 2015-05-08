/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.escape;

import java.io.File;
import java.util.Date;
import java.util.SortedMap;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.Direction;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.module.ObjectiveModule;
import pl.shg.arcade.api.module.Score;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;
import pl.shg.arcade.bukkit.plugin.ArcadeBukkitPlugin;

/**
 *
 * @author Aleksander
 */
public class EscapeModule extends ObjectiveModule implements BListener {
    private Explosion explosion;
    
    public EscapeModule() {
        super(new Date(2015, 04, 18), "explosion-escape", "1.0");
    }
    
    @Override
    public void disable() {
        Listeners.unregister(this);
    }
    
    @Override
    public void enable() {
        int id = Bukkit.getScheduler().runTaskTimer(ArcadeBukkitPlugin.getPlugin(),
                new Scheduler(this.explosion), 0L, 10L).getTaskId();
        Scheduler.setID(id);
        Arcade.getServer().getScheduler().runTask(id);
        
        Listeners.register(this);
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        this.explosion = new Explosion();
        this.explosion.setDirection(new Direction(0, 5, 0, 0F, 0F)); // TODO load from the configuration file
    }
    
    @Override
    public void unload() {
        
    }
    
    @Override
    public Score[] getMatchInfo(Team team) {
        return new Score[] {
            Score.byID(team, "escape", new Score(new String(),
                    Color.GOLD, "Gracze" + Color.RED + ": ", Color.DARK_AQUA + Color.BOLD + team.getPlayers().size()))
        };
    }
    
    @Override
    public Tutorial.Page getTutorial() {
        return new Tutorial.Page("Escape",
                "Twoim zadaniem jest przetrwanie niszczacego wszystko wybuchu.\n\n" +
                "Wygrywa osoba, która jako ostatnia przetrwa mecz.");
    }
    
    @Override
    public void makeScoreboard() {
        
    }
    
    @Override
    public boolean objectiveScored(Team team) {
        return true;
    }
    
    @Override
    public SortedMap<Integer, Team> sortTeams() {
        return null;
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = Arcade.getServer().getPlayer(e.getPlayer().getUniqueId());
        if (player.isObserver()) {
            return;
        } else if (e.getFrom().getBlockX() == e.getTo().getBlockX() || e.getFrom().getBlockZ() == e.getTo().getBlockZ()) {
            return;
        }
        
        Block block = e.getFrom().subtract(0, 1, 0).getBlock();
        // TOOD remove this shit
        if (block.getType() == Material.GLASS) {
            block.setType(Material.STAINED_GLASS);
        }
        
        if (block.getType() == Material.STAINED_GLASS) {
            DyeColor color = DyeColor.getByData(block.getData());
            switch (color) {
                case RED:
                    e.getPlayer().teleport(e.getTo().subtract(0, 1, 0));
                    break;
                case YELLOW:
                    block.setData(DyeColor.RED.getData());
                    break;
                case LIME:
                    block.setData(DyeColor.YELLOW.getData());
                    break;
                default:
                    block.setData(DyeColor.LIME.getData());
                    break;
            }
        }
    }
}
