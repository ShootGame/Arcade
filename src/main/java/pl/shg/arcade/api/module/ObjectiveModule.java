/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.module;

import java.util.Date;
import java.util.SortedMap;
import pl.shg.arcade.api.Arcade;
import pl.shg.arcade.api.map.Tutorial;
import pl.shg.arcade.api.match.Match;
import pl.shg.arcade.api.match.PlayerWinner;
import pl.shg.arcade.api.match.TeamWinner;
import pl.shg.arcade.api.match.Winner;
import pl.shg.arcade.api.team.Team;

/**
 *
 * @author Aleksander
 */
public abstract class ObjectiveModule extends Module {
    public ObjectiveModule(Date date, String id, String version) {
        super(date, id, version);
    }
    
    @Override
    public boolean hasObjective() {
        return true;
    }
    
    public Team getCurrentWinner() {
        return this.sortTeams().get(0);
    }
    
    public abstract Score[] getMatchInfo(Team team);
    
    public Tutorial.Page getTutorial() {
        return null;
    }
    
    @Deprecated
    public abstract void makeScoreboard();
    
    public abstract boolean objectiveScored(Team team);
    
    public abstract SortedMap<Integer, Team> sortTeams();
    
    public void updateObjectives() {
        for (Team team : Arcade.getTeams().getTeams()) {
            if (this.objectiveScored(team)) {
                Match match = Arcade.getMatches().getMatch();
                
                Winner winner = null;
                switch (match.getType()) {
                    case PLAYERS:
                        winner = new PlayerWinner(team.getPlayers().get(0));
                        break;
                    case TEAMS:
                        winner = new TeamWinner(team);
                        break;
                }
                
                match.end(winner);
                return;
            }
        }
    }
}
