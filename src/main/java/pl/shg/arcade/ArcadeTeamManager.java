/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade;

import java.util.ArrayList;
import java.util.List;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.kit.Kit;
import pl.shg.arcade.api.team.ChatChannel;
import pl.shg.arcade.api.team.GlobalChat;
import pl.shg.arcade.api.team.ObserverTeamBuilder;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.team.TeamManager;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class ArcadeTeamManager implements TeamManager {
    private final ChatChannel globalChannel;
    private List<Kit> kits = new ArrayList<>();
    private Team obs;
    private List<Team> teams;
    
    public ArcadeTeamManager() {
        this.globalChannel = new GlobalChat();
        this.obs = new Team(new ObserverTeamBuilder());
    }
    
    @Override
    public ChatChannel getChannel(String name) {
        Validate.notNull(name, "name can not be null");
        return this.getTeam(name).getChat();
    }
    
    @Override
    public ChatChannel getGlobalChannel() {
        return this.globalChannel;
    }
    
    @Override
    public Kit getKit(String name) {
        Validate.notNull(name, "name can not be null");
        for (Kit kit : this.kits) {
            if (kit.getID().toLowerCase().equals(name.toLowerCase())) {
                return kit;
            }
        }
        return null;
    }
    
    @Override
    public List<Kit> getKits() {
        return this.kits;
    }
    
    @Override
    public boolean hasKits() {
        return !this.kits.isEmpty();
    }
    
    @Override
    public void setKits(List<Kit> kits) {
        if (kits == null) {
            kits = new ArrayList<>();
        }
        this.kits = kits;
    }
    
    @Override
    public Team getObservers() {
        return this.obs;
    }
    
    @Override
    public void setObservers(Team obs) {
        Validate.notNull(obs, "obs can not be null");
        this.obs = obs;
    }
    
    @Override
    public Team getTeam(String name) {
        if (name == null) {
            return this.getObservers();
        }
        
        for (Team team : this.getTeams()) {
            if (team.getName().toLowerCase().contains(name.toLowerCase())) {
                return team;
            }
        }
        return null;
    }
    
    @Override
    public List<Team> getTeams() {
        return this.teams;
    }
    
    @Override
    public void setTeams(List<Team> teams) {
        Validate.notNull(teams, "teams can not be null");
        this.teams = teams;
    }
    
    @Override
    public void resetClasses() {
        for (Player player : Arcade.getServer().getOnlinePlayers()) {
            player.setArcadeClass(null);
        }
    }
}
