/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.blitz;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.Sound;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.map.team.Team;
import pl.shg.arcade.api.module.ObjectiveModule;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;
import pl.shg.arcade.bukkit.ScoreboardManager;

/**
 *
 * @author Aleksander
 */
public class BlitzModule extends ObjectiveModule implements BListener {
    public BlitzModule() {
        super(new Date(2015, 4, 26), "blitz", "1.0");
        this.getDocs().setDescription("Dodaje tryb gry, w którym wygrywa drużyna " +
                "w której ostatni zostaną… gracze.");
        this.deploy(true);
    }
    
    @Override
    public void disable() {
        Listeners.unregister(this);
    }
    
    @Override
    public void enable() {
        for (Team team : Arcade.getTeams().getTeams()) {
            ScoreboardManager.Sidebar.getScore(team.getDisplayName(), team.getPlayers().size());
        }
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        Listeners.register(this);
    }
    
    @Override
    public void unload() {
        
    }
    
    @Override
    public String[] getMatchInfo(Team team) {
        return new String[] {
            Color.GOLD + "Gracze" + Color.RED + ": " + Color.DARK_AQUA + Color.BOLD + team.getPlayers().size()
        };
    }
    
    @Override
    public Tutorial.Page getTutorial() {
        return new Tutorial.Page("Blitz",
                "Twoim zadaniem jest przetrwanie ataku druzyny przeciwnej.\n\n" +
                "Wygrywa druzyna, która jako ostatnia zostanie w grze.");
    }
    
    @Override
    public void makeScoreboard() {
        for (Team team : Arcade.getTeams().getTeams()) {
            ScoreboardManager.Sidebar.getScore(team.getDisplayName(), 0);
        }
    }
    
    @Override
    public boolean objectiveScored(Team team) {
        List<Team> kicked = new ArrayList<>();
        for (Team onlineTeam : Arcade.getTeams().getTeams()) {
            kicked.add(onlineTeam);
        }
        
        if (kicked.size() == 1) {
            if (kicked.get(0).equals(team)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public SortedMap<Integer, Team> sortTeams() {
        return null;
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = Arcade.getServer().getPlayer(e.getEntity().getUniqueId());
        Arcade.getPlayerManagement().playSound(player, Sound.ELIMINATION);
        ScoreboardManager.Sidebar.getScore(player.getTeam().getDisplayName(), player.getTeam().getPlayers().size());
        
        this.updateObjectives();
    }
}
