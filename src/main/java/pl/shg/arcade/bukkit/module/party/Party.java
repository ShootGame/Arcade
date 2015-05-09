/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.bukkit.module.party;

import java.io.File;
import java.util.Date;
import java.util.SortedMap;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.module.ObjectiveModule;
import pl.shg.arcade.api.module.Score;
import pl.shg.arcade.api.server.party.Partyable;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;
import pl.shg.arcade.bukkit.plugin.ModuleLoader;

/**
 *
 * @author Aleksander
 */
public abstract class Party extends ObjectiveModule implements BListener, Partyable {
    public Party(Date date, String id, String version) {
        super(date, "party-" + id, version);
    }
    
    @Override
    public void disable() {
        Listeners.unregister(this);
    }
    
    @Override
    public void enable() {
        Listeners.register(this);
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        
    }
    
    @Override
    public void unload() {
        
    }
    
    @Override
    public Score[] getMatchInfo(Team team) {
        return null;
    }
    
    @Override
    public Tutorial.Page getTutorial() {
        StringBuilder builder = new StringBuilder();
        for (String line : this.getPartyTutorial()) {
            builder.append(line).append("\n");
        }
        return new Tutorial.Page("Party - " + this.getID(), builder.toString());
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
    
    public static void registerPartyModules(ModuleLoader loader) {
        loader.register(DanceParty.class);
    }
}
