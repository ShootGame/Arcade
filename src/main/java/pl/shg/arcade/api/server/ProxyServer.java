/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2014
 */
package pl.shg.arcade.api.server;

import pl.shg.arcade.api.human.Player;
import pl.shg.commons.server.TargetServer;

/**
 *
 * @author Aleksander
 */
public interface ProxyServer {
    void connect(Player player, TargetServer server);
    
    String getProxyName();
}
