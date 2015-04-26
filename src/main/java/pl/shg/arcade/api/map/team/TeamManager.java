/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.map.team;

import java.util.List;
import pl.shg.arcade.api.map.team.kit.Kit;

/**
 *
 * @author Aleksander
 */
public interface TeamManager {
    ChatChannel getChannel(String name);
    
    ChatChannel getGlobalChannel();
    
    Kit getKit(String id);
    
    List<Kit> getKits();
    
    boolean hasKits();
    
    void setKits(List<Kit> kits);
    
    Team getObservers();
    
    void setObservers(Team obs);
    
    Team getTeam(String name);
    
    List<Team> getTeams();
    
    void setTeams(List<Team> teams);
    
    void resetClasses();
}
