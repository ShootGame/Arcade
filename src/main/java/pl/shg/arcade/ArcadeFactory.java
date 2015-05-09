/*
 * Copyright (C) 2015 TheMolkaPL - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Aleksander Jagiełło <themolkapl@gmail.com>, 2015
 */
package pl.shg.arcade;

import pl.shg.arcade.api.Plugin;
import pl.shg.arcade.api.PluginProperties;
import pl.shg.arcade.api.server.Server;
import pl.shg.arcade.api.util.Validate;

/**
 *
 * @author Aleksander
 */
public class ArcadeFactory {
    public static Plugin newInstance(Server server, PluginProperties properties) {
        Validate.notNull(server, "server can not be null");
        Validate.notNull(properties, "properties can not be null");
        return new PluginImpl(server, properties);
    }
}
