/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.monument;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Color;
import pl.shg.arcade.api.map.Block;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.Location;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.map.team.Team;
import pl.shg.arcade.api.module.ObjectiveModule;
import pl.shg.arcade.bukkit.Config;
import pl.shg.arcade.bukkit.ScoreboardManager;

/**
 *
 * @author Aleksander
 */
public class MonumentModule extends ObjectiveModule {
    private final HashMap<Team, List<Objective>> objectives = new HashMap<>();
    
    public MonumentModule() {
        super(new Date(2015, 03, 27), "monument", "1.0");
        this.deploy(true);
    }
    
    @Override
    public void disable() {}
    
    @Override
    public void enable() {}
    
    @Override
    public void load(File file) throws ConfigurationException {
        FileConfiguration config = Config.get(file);
        if (!Config.isSet(config, this) || !Config.hasOptions(config, this)) {
            return;
        }
        
        for (String teamName : Config.getOptions(config, this, "objectives")) {
            Team team = Arcade.getTeams().getTeam(teamName);
            if (team == null) {
                continue;
            }
            this.objectives.put(team, new ArrayList<Objective>());
            
            for (String objectiveName : Config.getOptions(config, this, "objectives." + teamName)) {
                Objective objective = new Objective(this, objectiveName, team);
                
                for (String monumentCoords : Config.getValueList(config, this, "objectives." + teamName + "." + objectiveName)) {
                    String[] coords = monumentCoords.split(":");
                    try {
                        int x = Integer.parseInt(coords[0]);
                        int y = Integer.parseInt(coords[1]);
                        int z = Integer.parseInt(coords[2]);
                        
                        Monument monument = new Monument(new Block(new Location(x, y, z)), objective);
                        objective.addMonument(monument);
                    } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                        Logger.getLogger(MonumentModule.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                this.objectives.get(team).add(objective);
            }
        }
    }
    
    @Override
    public void unload() {}
    
    @Override
    public String[] getMatchInfo(Team team) {
        List<String> info = new ArrayList<>();
        for (int i = 0; i < this.objectives.get(team).size(); i++) {
            Objective objective = this.objectives.get(team).get(i);
            StringBuilder builder = new StringBuilder();
            
            builder.append(Color.GOLD).append(Color.BOLD).append(objective.getDisplayName().trim()).append(Color.RESET);
            builder.append(Color.RED).append(" - ");
            switch (objective.getStatus()) {
                case DESTROYED:
                    builder.append(Color.DARK_RED).append(Color.ITALIC).append("ZNISZCZONY");
                    break;
                case TOUCHED: case UNTOUCHED:
                    builder.append(Color.GREEN).append(Color.ITALIC).append("AKTYWNY");
                    break;
            }
            builder.append(Color.RESET).append("\n");
            
            info.add(builder.toString());
        }
        return info.toArray(new String[info.size()]);
    }
    
    @Override
    public Tutorial.Page getTutorial() {
        return new Tutorial.Page("Destroy the monument",
                "Zadaniem Twojej druzyny jest zniszczenie wszyskich monumentów druzyny przeciwnej.\n\n" +
                "Wygrywa druzyna która jako pierwsza zlikwiduje wszystkie monumenty przeciwnika.");
    }
    
    @Override
    public void makeScoreboard() {
        int i = -1;
        for (Team team : this.objectives.keySet()) {
            ScoreboardManager.Sidebar.getScore(team.getID(), team.getDisplayName(), i);
            i--;
            
            for (Objective objective : this.objectives.get(team)) {
                ScoreboardManager.Sidebar.getScore(
                        objective.getScoreboardID(),
                        objective.getDisplayName(),
                        i,
                        "  " + Color.GREEN,
                        null);
                i--;
            }
        }
    }
    
    @Override
    public boolean objectiveScored(Team team) {
        int destroyed = 0, found = 0;
        
        for (Team objTeam : this.objectives.keySet()) {
            if (!team.equals(objTeam)) {
                for (Objective obj : this.objectives.get(objTeam)) {
                    found++;
                    if (obj.isDestroyed()) {
                        destroyed++;
                    }
                }
            }
        }
        return destroyed >= found;
    }
    
    @Override
    public SortedMap<Integer, Team> sortTeams() {
        SortedMap<Integer, Team> sorted = new TreeMap<>();
        for (Team team : this.objectives.keySet()) {
            int i = 0;
            for (Objective objective : this.objectives.get(team)) {
                i += objective.getMonuments().size() - objective.getDestroyed();
            }
            sorted.put(i, team);
        }
        return sorted;
    }
}
