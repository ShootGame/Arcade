/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.wool;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.module.ObjectiveModule;
import pl.shg.arcade.api.module.ScoreboardScore;
import pl.shg.arcade.api.team.Team;

/**
 *
 * @author Aleksander
 */
public class WoolModule extends ObjectiveModule {
    private final HashMap<Team, List<WoolMonument>> monuments = new HashMap<>();
    
    public WoolModule() {
        super(new Date(2015, 03, 26), "wool", "1.0");
    }
    
    @Override
    public void disable() {}
    
    @Override
    public void enable() {}
    
    @Override
    public void load(File file) throws ConfigurationException {
        
    }
    
    @Override
    public void unload() {
        
    }
    
    @Override
    public ScoreboardScore[] getMatchInfo(Team team) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Tutorial.Page getTutorial() {
        return new Tutorial.Page("Capture the wool",
                "Zadaniem Twojej druzyny jest zdobycie welny z pokojów druzyny przeciwnej\n" +
                "oraz przeniesienie jej na sojuszniczy spawn.\n\n" +
                "Wygrywa druzyna która jako pierwsza bedzie posiadac wszystkie wszystkie welny na swoim spawnie.");
    }
    
    @Override
    public void makeScoreboard() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean objectiveScored(Team team) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public SortedMap<Integer, Team> sortTeams() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public HashMap<Team, List<WoolMonument>> getAllMonuments() {
        return this.monuments;
    }
    
    public List<WoolMonument> getMonuments(Team owner) {
        return this.getAllMonuments().get(owner);
    }
    
    public void registerMonument(WoolMonument monument) {
        Team owner = monument.getWool().getOwner();
        if (!this.monuments.containsKey(owner)) {
            this.monuments.put(owner, new ArrayList<WoolMonument>());
        }
        this.monuments.get(owner).add(monument);
    }
}
