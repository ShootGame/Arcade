/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade;

import java.util.List;
import java.util.logging.Level;
import org.apache.commons.lang3.Validate;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.Log;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.match.PlayerWinner;
import pl.shg.arcade.api.match.TeamWinner;
import pl.shg.arcade.api.match.Winner;
import pl.shg.arcade.api.module.Module;
import pl.shg.arcade.api.module.ObjectiveModule;
import pl.shg.arcade.api.server.Server;
import pl.shg.arcade.api.tablist.TabList;
import pl.shg.arcade.api.team.Team;
import pl.shg.commons.server.ArcadeMatchStatus;

/**
 *
 * @author Aleksander
 */
public abstract class ArcadeServer implements Server {
    private boolean checkMinimum = true;
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
        if (Arcade.getMatches().getStatus() == ArcadeMatchStatus.RUNNING) {
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
    
    @Override
    public boolean isFull() {
        return this.getSlots() <= this.getConnectedPlayers().size();
    }
    
    public boolean isCheckMinimum() {
        return this.checkMinimum;
    }
    
    public void setCheckMinimum(boolean checkMinimum) {
        this.checkMinimum = checkMinimum;
    }
    
    private void checkEnd() {
        for (Team team : Arcade.getTeams().getTeams()) {
            List<Player> players = team.getPlayers();
            
            // Check the players amount
            if (this.isCheckMinimum() && players.size() < Team.MINIMUM) {
                Log.log(Level.INFO, "Konczenie meczu z powodu zbyt malej ilosci graczy.");
                Arcade.getMatches().getMatch().end();
                return;
            }
            
            // Check modules to finish the current match
            if (this.modulesAreScored(team)) {
                Winner winner = null;
                if (Arcade.getTeams().getTeams().size() == 1) {
                    winner = new PlayerWinner(team.getPlayers().get(0));
                } else {
                    winner = new TeamWinner(team);
                }
                
                Arcade.getMatches().getMatch().end(winner);
                return;
            }
        }
    }
    
    private boolean modulesAreScored(Team team) {
        for (Module module : Arcade.getModules().getActiveModules()) {
            if (module instanceof ObjectiveModule) {
                if (!((ObjectiveModule) module).objectiveScored(team)) {
                    return false;
                }
            }
        }
        return true;
    }
}
