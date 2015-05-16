/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade;

import java.util.logging.Level;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.match.MatchStatus;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.module.ObjectiveModule;
import pl.shg.arcade.api.server.Server;
import pl.shg.arcade.api.server.TabList;
import pl.shg.arcade.api.team.ObserverTeamBuilder;
import pl.shg.arcade.api.team.Team;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public abstract class ArcadeServer implements Server {
    private TabList tabList;
    
    @Override
    public void broadcast(String message) {
        Validate.notNull(message, "message can not be null");
        Log.log(Level.INFO, message);
        for (Player player : this.getConnectedPlayers()) {
            player.sendMessage(message);
        }
    }
    
    @Override
    public boolean bungeeCord() {
        return Arcade.getOptions().isBungeeCordEnabled();
    }
    
    @Override
    public void checkEndMatch() {
        if (Arcade.getMatches().getStatus() == MatchStatus.PLAYING) {
            this.checkEnd();
        }
    }
    
    @Override
    public TabList getGlobalTabList() {
        return this.tabList;
    }
    
    @Override
    public void setGlobalTabList(TabList tabList) {
        Validate.notNull(tabList, "tabList can not be null");
        this.tabList = tabList;
    }
    
    @Override
    public boolean isDev() {
        return this.getName().toLowerCase().startsWith("dev");
    }
    
    private void checkEnd() {
        for (Team team : Arcade.getTeams().getTeams()) {
            if (team.getID().equals(ObserverTeamBuilder.getTeamID())) {
                continue;
            }
            
            if (team.getPlayers().size() < team.getMinimum()) {
                Arcade.getMatches().getMatch().end();
                return;
            }
        }
        
        for (Module module : Arcade.getModules().getActiveModules()) {
            if (module instanceof ObjectiveModule) {
                ObjectiveModule objective = (ObjectiveModule) module;
                // TODO check
            }
        }
    }
}
