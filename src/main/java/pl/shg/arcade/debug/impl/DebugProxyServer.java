/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.debug.impl;

import pl.shg.arcade.api.human.Player;
import pl.shg.arcade.api.server.ProxyServer;
import pl.shg.commons.server.TargetServer;

/**
 *
 * @author Aleksander
 */
public class DebugProxyServer implements ProxyServer {
    @Override
    public void connect(Player player, TargetServer server) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getProxyName() {
        return null;
    }
}
