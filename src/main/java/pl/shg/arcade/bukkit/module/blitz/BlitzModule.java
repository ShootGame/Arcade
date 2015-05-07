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
import pl.shg.arcade.api.Sound;
import pl.shg.arcade.api.chat.Color;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.module.ObjectiveModule;
import pl.shg.arcade.api.module.Score;
import pl.shg.arcade.api.module.ScoreboardScore;
import pl.shg.arcade.api.team.Team;
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
                "w której ostatni zostaną gracze.");
        this.deploy(true);
    }
    
    @Override
    public void disable() {
        Listeners.unregister(this);
    }
    
    @Override
    public void enable() {
        Listeners.register(this);
        for (Team team : Arcade.getTeams().getTeams()) {
            ScoreboardManager.Sidebar.getScore(team.getID(), team.getDisplayName(), team.getPlayers().size());
        }
        ScoreboardManager.Sidebar.updateScoreboard();
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        
    }
    
    @Override
    public void unload() {
        
    }
    
    @Override
    public ScoreboardScore[] getMatchInfo(Team team) {
        return new ScoreboardScore[] {
            new Score(Color.GOLD, "Gracze" + Color.RED + ": ", Color.DARK_AQUA + Color.BOLD + team.getPlayers().size())
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
            ScoreboardManager.Sidebar.getScore(team.getID(), team.getDisplayName(), 0);
        }
    }
    
    @Override
    public boolean objectiveScored(Team team) {
        List<Team> stay = new ArrayList<>();
        for (Team onlineTeam : Arcade.getTeams().getTeams()) {
            if (!onlineTeam.getPlayers().isEmpty()) {
                stay.add(onlineTeam);
            }
        }
        
        if (stay.size() == 1) { // if list contains one team
            if (stay.get(0).equals(team)) { // if this one team is this team
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
        ScoreboardManager.Sidebar.getScore(player.getTeam().getID(), null, player.getTeam().getPlayers().size());
        
        this.updateObjectives();
    }
}
