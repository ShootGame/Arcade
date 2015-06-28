/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.channels.ChatChannel;
import pl.shg.arcade.api.channels.GlobalChannel;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.kit.Kit;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.team.TeamManager;

/**
 *
 * @author Aleksander
 */
public class ArcadeTeamManager implements TeamManager {
    private final ChatChannel globalChannel = new GlobalChannel();
    private final HashMap<String, Kit> kits = new HashMap<>();
    private Team obs;
    private List<Team> teams;
    
    @Override
    public ChatChannel getChannel(String name) {
        Validate.notNull(name);
        
        Team team = this.getTeam(name);
        if (team != null) {
            return team.getChat();
        } else {
            return null;
        }
    }
    
    @Override
    public ChatChannel getGlobalChannel() {
        return this.globalChannel;
    }
    
    @Override
    public Kit getKit(String name) {
        Validate.notNull(name);
        return this.kits.get(name.toLowerCase());
    }
    
    @Override
    public Collection<Kit> getKits() {
        return this.kits.values();
    }
    
    @Override
    public boolean hasKits() {
        return !this.kits.isEmpty();
    }
    
    @Override
    public void setKits(List<Kit> kits) {
        if (kits != null) {
            for (Kit kit : kits) {
                this.kits.put(kit.getID(), kit);
            }
        } else {
            this.kits.clear();
        }
    }
    
    @Override
    public Team getObservers() {
        return this.obs;
    }
    
    @Override
    public void setObservers(Team obs) {
        Validate.notNull(obs);
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
        Validate.notNull(teams);
        this.teams = teams;
    }
    
    @Override
    public void resetClasses() {
        for (Player player : Arcade.getServer().getConnectedPlayers()) {
            player.setArcadeClass(null);
        }
    }
}
