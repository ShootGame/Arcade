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
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.map.ConfigurationException;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.module.ObjectiveModule;
import pl.shg.arcade.api.module.Score;
import pl.shg.arcade.api.server.party.Partyable;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.util.Validate;
import pl.shg.arcade.bukkit.BListener;
import pl.shg.arcade.bukkit.Listeners;
import pl.shg.arcade.bukkit.module.AntiGriefModule;
import pl.shg.arcade.bukkit.module.AutoJoinModule;
import pl.shg.arcade.bukkit.module.AutoRespawnModule;
import pl.shg.arcade.bukkit.module.DeathMessagesModule;
import pl.shg.arcade.bukkit.module.JoinWhenRunningCancelModule;
import pl.shg.arcade.bukkit.module.NoRainModule;
import pl.shg.arcade.bukkit.module.NoThunderModule;
import pl.shg.arcade.bukkit.plugin.ModuleLoader;

/**
 *
 * @author Aleksander
 */
public abstract class Party extends ObjectiveModule implements BListener, Partyable {
    private final String name;
    
    public Party(Date date, String id, String name, String version) {
        super(date, "party-" + id, version);
        Validate.notNull(name, "name can not be null");
        this.name = name;
    }
    
    @Override
    public void loadDependencies() {
        this.addDependency(DependencyType.PARTY, AntiGriefModule.class);
        this.addDependency(DependencyType.PARTY, AutoJoinModule.class);
        this.addDependency(DependencyType.PARTY, AutoRespawnModule.class);
        this.addDependency(DependencyType.PARTY, DeathMessagesModule.class);
        this.addDependency(DependencyType.PARTY, JoinWhenRunningCancelModule.class);
        this.addDependency(DependencyType.PARTY, NoRainModule.class);
        this.addDependency(DependencyType.PARTY, NoThunderModule.class);
    }
    
    @Override
    public void disable() {
        Listeners.unregister(this);
        this.onMatchFinish();
    }
    
    @Override
    public void enable() {
        Listeners.register(this);
        this.onMatchStart();
    }
    
    @Override
    public void load(File file) throws ConfigurationException {
        for (Module module : Arcade.getModules().getActiveModules()) {
            if (module instanceof ObjectiveModule) {
                throw new ConfigurationException("Systemu gier party nie mozna laczyc z zadna inna gra.");
            }
        }
        this.loadParty(file);
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
        return new Tutorial.Page("Party - " + this.getName(), builder.toString());
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
    
    public String getName() {
        return this.name;
    }
    
    public void onMatchStart() {
        
    }
    
    public void onMatchFinish() {
        
    }
    
    public static void registerPartyModules(ModuleLoader loader) {
        loader.register(AnvilParty.class);
        loader.register(SheepsParty.class);
        loader.register(WoolscapeParty.class);
    }
}
