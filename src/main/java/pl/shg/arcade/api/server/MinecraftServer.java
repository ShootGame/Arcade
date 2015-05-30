/*
 * Copyright (C) 2014 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.server;

import java.util.Collection;
import java.util.UUID;
import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.menu.Menu;

/**
 *
 * @author Aleksander
 */
public interface MinecraftServer {
    Object createMenu(Menu menu);
    
    Collection<Player> getConnectedPlayers();
    
    String getName();
    
    Player getPlayer(String name);
    
    Player getPlayer(UUID uuid);
    
    int getSlots();
    
    boolean isFull();
    
    void shutdown();
}
