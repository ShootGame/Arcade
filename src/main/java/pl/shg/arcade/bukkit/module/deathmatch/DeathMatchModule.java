/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.bukkit.module.deathmatch;

import java.io.File;
import java.util.Date;
import java.util.SortedMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.map.team.Team;
import pl.shg.arcade.api.module.ModuleException;
import pl.shg.arcade.api.module.ObjectiveModule;
import pl.shg.arcade.api.module.docs.ConfigurationDoc;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.Listeners;
import pl.shg.arcade.bukkit.ScoreboardManager;
import pl.shg.arcade.bukkit.module.lib.Points;

/**
 *
 * @author Aleksander
 */
public class DeathMatchModule extends ObjectiveModule implements BListener {
    protected int maxScore = 0;
    
    public DeathMatchModule() {
        super(new Date(2014, 11, 15), "death-match", "1.0");
        this.addDependency(DependencyType.STRONG, Points.class);
        this.addExample(new ConfigurationDoc(false, ConfigurationDoc.Type.INT) {
            @Override
            public String getPrefix() {
                return "Ustaw ilość punktów (zabić) po ilu mecz ma się zakończyć, " +
                        "a drużyna wygrać. Domyślnie jest to 300 punktów.";
            }
            
            @Override
            public String[] getCode() {
                return new String[] {
                    "death-match:",
                    "  max-score: 300"
                };
            }
        });
        this.deploy(true);
    }
    
    @Override
    public void disable() {}
    
    @Override
    public void enable() {}
    
    @Override
    public void load(File file) throws ModuleException {
        FileConfiguration config = Config.get(file);
        if (!Config.isSet(config, this)) {
            return;
        }
        
        Listeners.register(this);
        if (!Config.hasOptions(config, this)) {
            return;
        }
        
        for (String option : Config.getOptions(config, this)) {
            switch (option.toLowerCase()) {
                case "max-score":
                    this.maxScore = Config.getValueInt(config, this, option);
                    Points.addMaxScore(this.maxScore);
                    break;
            }
        }
    }
    
    @Override
    public void unload() {
        Listeners.unregister(this);
    }
    
    @Override
    public String[] getMatchInfo(Team team) {
        return null;
    }
    
    @Override
    public Tutorial.Page getTutorial() {
        return new Tutorial.Page("Team-death match",
                "Zadaniem Twojej druzyny jest zabicie jak najwiecej graczy z druzyny przeciwnej.\n\n" +
                "Wygrywa druzyna która zabije " + this.maxScore + " przeciwników.");
    }
    
    @Override
    public void makeScoreboard() {
        for (Team team : Arcade.getTeams().getTeams()) {
            ScoreboardManager.Sidebar.getScore(team.getID(), team.getDisplayName(), 0);
        }
    }
    
    @Override
    public boolean objectiveScored(Team team) {
        return Points.getLibrary().objectiveScored(team);
    }
    
    @Override
    public SortedMap sortTeams() {
        return Points.getLibrary().sortTeams();
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player killer = e.getEntity().getKiller();
        if (killer != null) {
            this.handleDeath(killer);
        }
    }
    
    private void handleDeath(Player player) {
        Team team = Arcade.getServer().getPlayer(player.getUniqueId()).getTeam();
        Points.addOne(team);
        
        ScoreboardManager.Sidebar.getScore(team.getID(), null, Points.get(team));
        ScoreboardManager.Sidebar.updateScoreboard();
    }
}
