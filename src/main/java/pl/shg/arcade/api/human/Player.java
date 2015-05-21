/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.human;

import pl.shg.arcade.api.team.Team;
import pl.shg.commons.server.TargetServer;

/**
 *
 * @author Aleksander
 */
public interface Player extends Classable, MinecraftPlayer, NamnedPlayer {
    void connect(TargetServer server);
    
    void connect(String server);
    
    Object getPlayer();
    
    Team getTeam();
    
    boolean isObserver();
    
    boolean isPremium();
    
    boolean isSpying();
    
    boolean isStaff();
    
    boolean isTeam(Team team);
    
    void kick();
    
    boolean kickToLobby();
    
    boolean kickToLobby(String reason);
    
    void setSpying(boolean spying);
    
    void setTeam(Team team);
    
    void resetPlayerState();
    
    void updateTag();
}
