/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server.status;

import pl.shg.arcade.api.human.Player;

/**
 *
 * @author Aleksander
 */
public interface ServerStatus {
    boolean enabled();
    
    String canConnect(Player player);
    
    int online();
    
    String rawMOTD();
    
    int slots();
}
