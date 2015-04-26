/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade.api.server;

import pl.shg.arcade.api.command.ConsoleSender;

/**
 *
 * @author Aleksander
 */
public class BroadcasterSender extends ConsoleSender {
    @Override
    public String getName() {
        return ArcadeServer.BroadcasterLoader.DEFAULT_NAME;
    }
    
    @Override
    public boolean isConsole() {
        return true; // probalby sender is the server, so yes
    }
}
